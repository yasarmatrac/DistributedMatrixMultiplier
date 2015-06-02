package bilm463proje.client.response;

/**
 * Sunucudan İstemciye gelen OK mesajını temsil eden cevap sınıfıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientAffirmativeResponse implements ClientResponse {

    private int value;

    /**
     * Cevap mesajı oluşturulurken random sayı girilir.
     *
     * @param value random sayı
     */
    public ClientAffirmativeResponse(int value) {
        this.value = value;
    }

    /**
     * Sunucunun ürettiği random sayıyı verir.
     *
     * @return random sayı
     */
    public int getValue() {
        return value;
    }

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */
    @Override
    public ClientResponseType getType() {
        return ClientResponseType.OK;
    }

}
