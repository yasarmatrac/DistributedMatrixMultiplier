package bilm463proje.exception;

/**
 * Matrix çarpımı için ilk matrix'in sütun sayısıyla ikinci matrix'in satır
 * sayısı eşit olmadığında fırlatılacak istisna sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class NotEqualColumnRow extends Exception {

    /**
     * Default Constructor
     */
    public NotEqualColumnRow() {
    }

    /**
     * Hata mesajlı Constructor
     *
     * @param message Hata mesajı
     */
    public NotEqualColumnRow(String message) {
        super(message);
    }

}
