package bilm463proje.exception;

/**
 * Oluşturulacak matrixin her satırındaki kolon sayısı eşit olmadğında
 * fırlatılacak hata mesajı
 *
 * @author 07051102,07051124,07050941
 */
public class IrregularMatrixException extends Exception {

    /**
     * Default Constructor
     */
    public IrregularMatrixException() {
    }

    /**
     * Hata mesajlı Constructor
     *
     * @param message Hata mesajı
     */
    public IrregularMatrixException(String message) {
        super(message);
    }

}
