package bilm463proje.node.response;

/**
 * Sunucudan İşlemci Node'a gelen OK mesajını temsil eden cevap sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class NodeAffirmativeResponse implements NodeResponse {

    private int connectionId;
    private int value;

    /**
     * Cevap mesajı oluşturulurken random sayı girilir.
     *
     * @param connectionId Bağlantı Adresi
     * @param value Rastgele sayı
     */
    public NodeAffirmativeResponse(int connectionId, int value) {
        this.connectionId = connectionId;
        this.value = value;
    }

    /**
     * 
     * @return Rastgele sayı
     */
    public int getValue() {
        return value;
    }

    /**
     * 
     * @return Bağlantı Adresi
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */
    @Override
    public NodeResponseType getType() {
        return NodeResponseType.OK;
    }

}
