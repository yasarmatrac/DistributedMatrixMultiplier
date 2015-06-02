package bilm463proje.exception;

/**
 * Geçersiz istek mesajı alındığında fırlatılacak istisna sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class InvalidRequestException extends Exception {

    /**
     * Default Constructor
     */
    public InvalidRequestException() {
    }

    /**
     * Hata mesajlı Constructor
     *
     * @param message Hata mesajı
     */
    public InvalidRequestException(String message) {
        super(message);
    }

}
