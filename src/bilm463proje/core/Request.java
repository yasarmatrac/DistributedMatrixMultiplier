package bilm463proje.core;

import java.io.Serializable;

/**
 * İstek mesajlarını temsil eden soyut sınıftır. Tüm istek mesajları bu sınıftan
 * kalıtım alır.
 *
 * @author 07051102,07051124,07050941
 */
public interface Request extends Serializable {

    /**
     * İstek mesaj tipini döndürür.
     *
     * @return istek mesaj tipi
     */
    public Enum getType();
}
