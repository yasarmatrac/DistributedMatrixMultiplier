/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bilm463proje.server.request;

/**
 * Sunucudan işlemci node'a yollanılacak satır sütun çarpma istek mesajını
 * temsil eden sınıftır
 *
 * @author 07051102,07051124,07050941
 */
public class ServerCalculateRequest implements ServerRequest {

    private int rowIndex;
    private int columnIndex;
    private double[] row;
    private double[] column;

    /**
     *
     * @param rowIndex Çarpılacak satır indeksi
     * @param columnIndex Çarpılacak sütun indeksi
     * @param row Çarpılacak satır
     * @param column Çarpılacak sütun
     */
    public ServerCalculateRequest(int rowIndex, int columnIndex, double[] row, double[] column) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.row = row;
        this.column = column;
    }

    /**
     *
     * @return Çarpılacak sütun döndürülür
     */
    public double[] getColumn() {
        return column;
    }

    /**
     *
     * @return Çarpılacak satır döndürülür
     */
    public double[] getRow() {
        return row;
    }

    /**
     *
     * @return Çarpılacak sütun indeksi döndürülür
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     *
     * @return Çarpılacak satır indeksi döndürülür
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public ServerRequestType getType() {
        return ServerRequestType.CALCULATE;
    }

}
