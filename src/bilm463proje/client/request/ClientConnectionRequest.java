package bilm463proje.client.request;

/**
 * İstemci sunucuya bağlantı isteğini bu sınıf aracılığı iletir.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientConnectionRequest implements ClientRequest {

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public ClientRequestType getType() {
        return ClientRequestType.NEW_CONNECTION;
    }

}
