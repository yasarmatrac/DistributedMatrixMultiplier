package bilm463proje.exception;

/**
 * İşlemci node olmadığında fırlatılacak istisna sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class HasNoNodeException extends Exception {

    /**
     * Default Constructor
     */
    public HasNoNodeException() {
    }

    /**
     * Hata mesajlı Constructor
     *
     * @param message Hata mesajı
     */
    public HasNoNodeException(String message) {
        super(message);
    }

}
