package bilm463proje.client.request;

import bilm463proje.server.Matrix;

/**
 * İstemci sunucuya matrixleri çarpma isteğini bu sınıf aracılığı iletir.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientMatrixMultipleRequest implements ClientRequest {

    private Matrix matrixA;
    private Matrix matrixB;

    /**
     * İstek sınıfı oluşturulurken çarpılcak olan matrixleri alır. Sırası
     * önemlidir.
     *
     * @param matrixA ilk matrix
     * @param matrixB ikinci matrix
     */
    public ClientMatrixMultipleRequest(Matrix matrixA, Matrix matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    /**
     * İstek mesajı tipini döndürür.
     *
     * @return İstek mesajı tipi
     */
    @Override
    public ClientRequestType getType() {
        return ClientRequestType.MULTIPLY_MATRIX;
    }

    /**
     * İlk matrixi döndürür
     *
     * @return ilk matrix
     */
    public Matrix getMatrixA() {
        return matrixA;
    }

    /**
     * İkinci matrixi döndürür
     *
     * @return ikinci matrix
     */
    public Matrix getMatrixB() {
        return matrixB;
    }

}
