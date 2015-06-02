package bilm463proje.node.request;

/**
 * İşlemci Node'un sunucuya bağlantı isteğini bu sınıf aracılığı iletir.
 *
 * @author 07051102,07051124,07050941
 */
public class NodeConnectionRequest implements NodeRequest {

    private int connectionId;

    /**
     * İşlemci Node'un bağlantı Id'sini içeren Constructor
     *
     * @param connectionId İşlemci Node bağlantı Id
     */
    public NodeConnectionRequest(int connectionId) {
        this.connectionId = connectionId;
    }

    /**
     *
     * @return İşlemci Node bağlantı Id döndürür
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public NodeRequestType getType() {
        return NodeRequestType.CONNECT;
    }

}
