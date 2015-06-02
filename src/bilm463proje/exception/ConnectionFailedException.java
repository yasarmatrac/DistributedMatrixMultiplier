package bilm463proje.exception;

/**
 * Başarısız bağlantı isteğinde fırlatılacak istisna sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class ConnectionFailedException extends Exception {

    /**
     * Default Constructor
     */
    public ConnectionFailedException() {
    }

    /**
     * Hata mesajlı Constructor
     *
     * @param message Hata mesajı
     */
    public ConnectionFailedException(String message) {
        super(message);
    }

}
