package bilm463proje.client.request;

/**
 * İstemci sunucuya ACK mesajını bu sınıf aracılığı iletir.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientAcknowledgementRequest implements ClientRequest {

    private int value;

    /**
     * 
     * @param value Sunucunun gönderdiği random sayıy
     */
    public ClientAcknowledgementRequest(int value) {
        this.value = value;
    }

    /**
     * Sunucunun gönderdiği random sayıyı tekrar sunucuya yollar.
     *
     * @return Sunucunun gönderdiği random sayı
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
    public ClientRequestType getType() {
        return ClientRequestType.ACKNOWLEDGEMENT;
    }

}
