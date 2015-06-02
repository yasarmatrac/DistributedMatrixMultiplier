package bilm463proje.server;

import bilm463proje.exception.HasNoNodeException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Matrix çarpınımın işlemci node'lara dağıtan sınıf.
 *
 * @author 07051102,07051124,07050941
 */
class MatrixMultipleCalculator {

    private NodeConnectionManager nodeConnectionManager;

    /**
     *
     * @param nodeConnectionManager Packetleri yollayabilmesi için node
     * bağlantılarını tutan listeyi referans olarak alır
     */
    MatrixMultipleCalculator(NodeConnectionManager nodeConnectionManager) {
        this.nodeConnectionManager = nodeConnectionManager;
    }

    /**
     *
     * @param executor threadleri kontrol eden pool referansı
     * @param connection satır sütun çarpma göreninin gönderileceği Node
     * bağlantısı
     * @param task satır sütun çarpma görevi
     * @return
     */
    private Future<RowColumnMultipleResult> dispatch(ExecutorService executor,
            NodeConnection connection,
            RowColumnMultipleTask task) {
        task.setConnection(connection);
        return executor.submit(task);
    }

    /**
     * ilk matrix'in satırları ve ikinci matrixlerin sütunları alınarak satır
     * sütun çarpımı görevleri oluşturulur. nodeConnectionManager ile müsait
     * olan bağlantı alınır ve node'a işlem yapması için yollanır. dağıtılan
     * nodelardan alınan cevaplar toplanarak sonuç matrixi oluşturur.
     *
     * @param multipleTask matrix çarpın görevi
     * @return sonuç matrix
     * @throws HasNoNodeException işlemci node olmadığında alınacak bağlantı
     * hatası
     */
    public Matrix calculateMatrixMultiple(MatrixMultipleTask multipleTask) throws HasNoNodeException {
        ExecutorService executor = Executors.newWorkStealingPool();
        Matrix resultMatrix = new Matrix(multipleTask.getMatrixA().getRowLength(), multipleTask.getMatrixB().getColumnLength());
        List<RowColumnMultipleTask> rcMultipleTasks = multipleTask.getRowColumnMultipleTasks();

        do {
            for (RowColumnMultipleTask rcMultipleTask : rcMultipleTasks) {
                if (rcMultipleTask.isCompleted() == false && rcMultipleTask.isRunning() == false) {
                    try {
                        NodeConnection connection = nodeConnectionManager.takeConnection();
                        Future<RowColumnMultipleResult> future = dispatch(executor, connection, rcMultipleTask);
                        new Thread(() -> {
                            try {
                                RowColumnMultipleResult result = future.get(3, TimeUnit.SECONDS);
                                nodeConnectionManager.leaveConnection(connection);
                                resultMatrix.setPoint(result.getRowIndex(), result.getColumnIndex(), result.getResult());
                            } catch (InterruptedException | ExecutionException ex) {
                                Logger.getLogger(MatrixMultipleCalculator.class.getName()).log(Level.SEVERE, null, ex);
                                rcMultipleTask.setRunning(false);
                            } catch (TimeoutException ex) {
                                System.err.println(connection.getNodeHost() + ":" + connection.getNodePort() + " timed out. Connection removed");
                                nodeConnectionManager.removeConnection(connection);
                                rcMultipleTask.setRunning(false);
                            }
                        }).start();
                    } catch (HasNoNodeException ex) {
                        Logger.getLogger(MatrixMultipleCalculator.class.getName()).log(Level.SEVERE, null, ex);
                        throw ex;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MatrixMultipleCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } while (multipleTask.isCompleted() == false);
        return resultMatrix;
    }

}
