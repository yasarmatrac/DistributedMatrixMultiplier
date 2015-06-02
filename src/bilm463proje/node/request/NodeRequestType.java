package bilm463proje.node.request;

/**
 * İşlemci Node'dan sunucuya gidecek istek mesajı tipleri
 * 
 * @author 07051102,07051124,07050941
 */
public enum NodeRequestType {

    /**
     * Bağlantı bilgilerini itek mesaj tipi
     */
    CONNECTION_INFORMATION,

    /**
     * Bağlantı istek mesaj tipi
     */
    CONNECT,

    /**
     * Bağlantı onayı doğrulama istek mesaj tipi
     */
    ACK
}
