package bilm463proje.client.response;

import bilm463proje.core.Response;

/**
 * Sunucudan istemciye yollanılacak cevap soyut sınıfıdır. Diğer cevap
 * nesnesileri bu sınıftan kalıtım alır
 *
 * @author 07051102,07051124,07050941
 */
public interface ClientResponse extends Response {

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */ 
    @Override
    public ClientResponseType getType();

}
