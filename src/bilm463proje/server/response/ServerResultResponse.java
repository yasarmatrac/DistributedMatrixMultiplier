package bilm463proje.server.response;

/**
 * İşlemci Node'dan sunucuya satır sütun çarpımı sonucunu temsil eden cevap
 * mesajıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class ServerResultResponse implements ServerResponse {

    private int rowIndex;
    private int columnIndex;
    private double result;

    /**
     *
     * @param rowIndex Çarpılan ilk matrixin satır indeksi
     * @param columnIndex Çarpılan ikinci matriksin Sütun indeksi
     * @param result Çarpım sonucu
     */
    public ServerResultResponse(int rowIndex, int columnIndex, double result) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.result = result;
    }

    /**
     *
     * @return Çarpılan ikinci matriksin Sütun indeksi döndürür
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     *
     * @return Çarpılan ilk matrixin satır indeksi döndürür
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     *
     * @return Çarpım sonucu döndürür
     */
    public double getResult() {
        return result;
    }

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */
    @Override
    public ServerResponseType getType() {
        return ServerResponseType.RESULT;
    }

}
