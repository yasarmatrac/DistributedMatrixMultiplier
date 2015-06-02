package bilm463proje.client.response;

import bilm463proje.server.Matrix;

/**
 * Sunucudan istemciye matrix çarpım sonucunu temsil eden cevap mesajıdır.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientMatrixMultipleResponse implements ClientResponse {

    private Matrix matrix;

    /**
     * Çarpım sonucu elde edilen matrixi alır.
     *
     * @param matrix Sonuç matrix
     */
    public ClientMatrixMultipleResponse(Matrix matrix) {
        this.matrix = matrix;
    }

    /**
     * Sonuç matrixi döndürür
     *
     * @return çarpım sonucu matrix
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * Cevap mesajının tipini döndürür
     *
     * @return Cevap mesajının tipi
     */
    @Override
    public ClientResponseType getType() {
        return ClientResponseType.RESULT;
    }

}
