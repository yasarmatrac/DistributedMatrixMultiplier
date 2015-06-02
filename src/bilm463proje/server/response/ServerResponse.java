
package bilm463proje.server.response;

import bilm463proje.core.Response;

/**
 * İşlemci node'dan sunucuya yollanılacak cevap soyut sınıfıdır. Diğer cevap
 * nesnesileri bu sınıftan kalıtım alır
 *
 * @author 07051102,07051124,07050941
 */
public interface ServerResponse extends Response {

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */ 
    @Override
    public ServerResponseType getType();

}
