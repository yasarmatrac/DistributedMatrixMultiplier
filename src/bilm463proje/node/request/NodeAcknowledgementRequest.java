package bilm463proje.node.request;

/**
 * İşlemci Node'dan sunucuya ACK mesajını bu sınıf aracılığı iletir. Sunucu
 * tarafından gelen restgele sayıyı doğrulama amaçlı tekrar
 *
 * @author 07051102,07051124,07050941
 */
public class NodeAcknowledgementRequest implements NodeRequest {

    private int value;

    /**
     * Rastgele sayı içeren Constructor
     *
     * @param value Rastgele sayı
     */
    public NodeAcknowledgementRequest(int value) {
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
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public NodeRequestType getType() {
        return NodeRequestType.ACK;
    }
}
