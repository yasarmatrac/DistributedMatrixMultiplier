package bilm463proje.client.request;

import bilm463proje.core.Request;

/**
 * İstemciden sunucuya yollanılacak isteklerin soyut sınıfıdır. Diğer istek
 * nesnesileri bu sınıftan kalıtım alır
 *
 * @author 07051102,07051124,07050941
 */
public interface ClientRequest extends Request {

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public ClientRequestType getType();

}
