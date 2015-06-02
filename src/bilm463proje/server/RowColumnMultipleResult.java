/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bilm463proje.server;

/**
 * Satır sütun çarpımının sonucunu temsileden sınıf. RowColumnMultipleTask
 * nesnesinin callable fonksiyonu sayesinde döndürülür.
 *
 * @author 07051102,07051124,07050941
 */
public class RowColumnMultipleResult {

    private int rowIndex;
    private int columnIndex;
    private double result;
    private NodeConnection connection;

    /**
     *
     * @param connection Sonucu işlemci sınıftan alan node bağlantısı
     * @param rowIndex sonuç satır indeksi
     * @param columnIndex sonuç sütun indeksi
     * @param result sonuc değeri
     */
    public RowColumnMultipleResult(NodeConnection connection, int rowIndex, int columnIndex, double result) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.result = result;
        this.connection = connection;
    }

    /**
     *
     * @return Sonucu işlemci sınıftan alan node bağlantısı döndürür
     */
    public NodeConnection getConnection() {
        return connection;
    }

    /**
     *
     * @return sonuç sütun indeksi döndürür
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     *
     * @return sonu. satır indeksi döndürür
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     *
     * @return sonuc değeri döndürür
     */
    public double getResult() {
        return result;
    }

}
