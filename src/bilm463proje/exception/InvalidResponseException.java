package bilm463proje.exception;

/**
 * Geçersiz cevap mesajı alındığında fırlatılacak istisna sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class InvalidResponseException extends Exception {

    /**
     * Default Constructor
     */
    public InvalidResponseException() {
    }

    /**
     * Hata mesajlı Constructor
     *
     * @param message Hata mesajı
     */
    public InvalidResponseException(String message) {
        super(message);
    }

}
