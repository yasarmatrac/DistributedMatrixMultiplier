package bilm463proje.node.response;

import bilm463proje.core.Response;

/**
 * Sunucudan işlemci Node'a yollanılacak cecap soyut sınıfıdır. Diğer cevap
 * nesnesileri bu sınıftan kalıtım alır
 *
 * @author 07051102,07051124,07050941
 */
public interface NodeResponse extends Response {

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */
    @Override
    public abstract NodeResponseType getType();

}
