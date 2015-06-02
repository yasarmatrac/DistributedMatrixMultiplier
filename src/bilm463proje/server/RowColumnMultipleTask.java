/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bilm463proje.server;

import bilm463proje.server.request.ServerCalculateRequest;
import bilm463proje.server.response.ServerResultResponse;
import java.util.concurrent.Callable;

/**
 * Satır Sütun çarpımı görevini temsil eden sınıf. Callable nesnesinden miras
 * alır. Çalışması sonucu RowColumnMultipleResult sınıfını döndürür
 *
 * @author 07051102,07051124,07050941
 */
public class RowColumnMultipleTask implements Callable<RowColumnMultipleResult> {

    private NodeConnection connection;

    private boolean completed = false;
    private boolean running = false;

    private int columnIndex;
    private int rowIndex;
    private double[] column;
    private double[] row;

    /**
     *
     * @param rowIndex Çarpılan ilk matrixin satır indeksi
     * @param columnIndex Çarpılan ikinci matriksin Sütun indeksi
     * @param row Çarpılan ilk matrixin satırı
     * @param column Çarpılan ikinci matriksin sütunu
     */
    public RowColumnMultipleTask(int rowIndex, int columnIndex, double[] row, double[] column) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.column = column;
        this.row = row;
    }

    /**
     *
     * @param connection görevin yapılması için tayin edilcek bağlantı
     */
    public void setConnection(NodeConnection connection) {
        this.connection = connection;
    }

    /**
     *
     * @return
     */
    public double[] getColumn() {
        return column;
    }

    /**
     *
     * @return
     */
    public double[] getRow() {
        return row;
    }

    /**
     *
     * @return
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     *
     * @return
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     *
     * @return
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     *
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Callable sınıfından alınan soyut method implement edilmiştir. Satır sütun
     * işlemci node nodeconnection ile gönderilir. sonuç alınır     *
     *
     * @return satır sütun çarpım sonucu
     * @throws Exception
     */
    @Override
    public RowColumnMultipleResult call() throws Exception {
        try {
            running = true;
            ServerCalculateRequest request = new ServerCalculateRequest(rowIndex, columnIndex, getRow(), getColumn());
            connection.sendMultipleRequest(request);
            ServerResultResponse response = connection.receiveMultipleResponse();
            RowColumnMultipleResult result = new RowColumnMultipleResult(connection, getRowIndex(), getColumnIndex(), response.getResult());
            completed = true;
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage() + " - " + e.getCause());
            throw e;
        }
    }

}
