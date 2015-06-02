package bilm463proje.client.response;

/**
 * Sunucudan istemciye hata mesajını temsil eden cevap sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientErrorResponse implements ClientResponse {

    private String message;

    /**
     * Sınıf oluşturulurken hata mesajını alır.
     *
     * @param message Hata mesaj
     */
    public ClientErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Hata mesajını döndürür
     *
     * @return hata mesajı
     */
    public String getMessage() {
        return message;
    }

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */
    @Override
    public ClientResponseType getType() {
        return ClientResponseType.ERROR;
    }

}
