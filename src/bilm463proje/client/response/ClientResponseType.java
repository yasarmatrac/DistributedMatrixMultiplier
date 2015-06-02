package bilm463proje.client.response;

/**
 * Sunucudan istemciye gidebilecek cevapların tiplerini içeren enum sınıf
 *
 * @author 07051102,07051124,07050941
 */
public enum ClientResponseType {

    /**
     * Bağlantı onay cevap mesajı tipi
     */
    OK,
    /**
     * Hata cevap mesajı tipi
     */
    ERROR,
    /**
     * Sonuç cevap mesajı tipi
     */
    RESULT
}
