
package bilm463proje.server.request;

import bilm463proje.core.Request;

/**
 * Sunucudan  işlemci node'a yollanılacak isteklerin soyut sınıfıdır. Diğer istek
 * nesnesileri bu sınıftan kalıtım alır
 *
 * @author 07051102,07051124,07050941
 */
public interface ServerRequest extends Request {

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public ServerRequestType getType();

}
