package bilm463proje.core;

import java.io.Serializable;

/**
 * Cevap mesajlarını temsil eden soyut sınıftır. Tüm cevap mesajları bu sınıftan
 * kalıtım alır.
 *
 * @author 07051102,07051124,07050941
 */
public interface Response extends Serializable {

    /**
     * Cevap mesaj tipini döndürür.
     *
     * @return Cevap mesaj tipi
     */
    public Enum getType();
}
