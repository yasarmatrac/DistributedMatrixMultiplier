package bilm463proje.node.request;

import bilm463proje.core.Request;

/**
 * İşlemci Node'dan sunucuya yollanılacak isteklerin soyut sınıfıdır. Diğer istek
 * nesnesileri bu sınıftan kalıtım alır
 *
 * @author 07051102,07051124,07050941
 */
public interface NodeRequest extends Request {

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public NodeRequestType getType();

}
