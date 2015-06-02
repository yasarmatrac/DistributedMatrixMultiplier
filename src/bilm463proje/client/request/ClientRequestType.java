package bilm463proje.client.request;

/**
 * İstemci istek tiplerini içeren enum sınıftır.
 *
 * @author 07051102,07051124,07050941
 */
public enum ClientRequestType {

    /**
     * Yeni bağlantı isteği mesaj tipi
     */
    NEW_CONNECTION,
    /**
     * Bağlantı isteği doğrulama mesaj tipi
     */
    ACKNOWLEDGEMENT,
    /**
     * Matrix çarpımı isteği mesaj tipi
     */
    MULTIPLY_MATRIX
}
