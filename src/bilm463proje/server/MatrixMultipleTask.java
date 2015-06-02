package bilm463proje.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Çarpılcak olan matrixleri içeren görev nesnesi
 *
 * @author 07051102,07051124,07050941
 */
public class MatrixMultipleTask {

    private Matrix matrixA;
    private Matrix matrixB;
    private List<RowColumnMultipleTask> rowColumnMultipleTasks;

    /**
     *
     * @param matrixA çarpılacak ilk matrix
     * @param matrixB çarpılacak ikinci matrix
     */
    public MatrixMultipleTask(Matrix matrixA, Matrix matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        rowColumnMultipleTasks = new ArrayList<>();
        initializeRowColumnMultipleTask();
    }

    /**
     *
     * @return çarpılacak ilk matrix
     */
    public Matrix getMatrixA() {
        return matrixA;
    }

    /**
     *
     * @return çarpılacak ikinci matrix
     */
    public Matrix getMatrixB() {
        return matrixB;
    }

    /**
     * Çarpılması gereken satır ve sütunları hazırlayan fonksiyon
     */
    private void initializeRowColumnMultipleTask() {
        for (int i = 0; i < matrixA.getRowLength(); i++) {
            for (int j = 0; j < matrixB.getColumnLength(); j++) {
                double[] row = matrixA.getRow(i);
                double[] column = matrixB.getColumn(j);
                RowColumnMultipleTask rcmTask = new RowColumnMultipleTask(i, j, row, column);
                rowColumnMultipleTasks.add(rcmTask);
            }
        }
    }

    /**
     * Çarpılması gereken satır ve sütunları RowColumnMultipleTask olarak
     * döndüren fonksiyon
     *
     * @return Çarpılacak satır ve sütun görevleri
     */
    public List<RowColumnMultipleTask> getRowColumnMultipleTasks() {
        return rowColumnMultipleTasks;
    }

    /**
     * 
     * @return Çarpma işlemi bitip bitmediğini döndürür
     */
    public boolean isCompleted() {
        for (RowColumnMultipleTask rowColumnMultipleTask : rowColumnMultipleTasks) {
            if (rowColumnMultipleTask.isCompleted() == false) {
                return false;
            }
        }
        return true;
    }
}
