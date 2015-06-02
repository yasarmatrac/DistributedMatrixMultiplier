package bilm463proje.client;

import bilm463proje.exception.IrregularMatrixException;
import bilm463proje.server.Matrix;

/**
 * Matrix nesnesi yardımcı sınıftır. TextArea'dan okunan texti Matrix nesnesine
 * çevirir.
 *
 * @author 07051102,07051124,07050941
 */
public class MatrixHelper {

    /**
     * TextArea'dan okunan texti Matrix nesnesine çevirir.
     *
     * @param str text area'dan alınan string
     * @return String'den okunan matrix
     * @throws IrregularMatrixException matrix satırındaki kolonlar eşit olmadığında atılır.
     * @throws NumberFormatException beklenmeyen formatta input geldiğinde fırlatılır
     */
    public static Matrix convertTextToMatrix(String str) throws IrregularMatrixException, NumberFormatException {
        String[] rows = str.split("\n");
        String[][] cells = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] rowColumns = rows[i].split(" ");
            cells[i] = rowColumns;
        }
        for (int i = 0; i < cells.length - 1; i++) {
            if (cells[i].length != cells[i + 1].length) {
                throw new IrregularMatrixException();
            }
        }
        double[][] matrixData = new double[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                matrixData[i][j] = Double.valueOf(cells[i][j]);
            }
        }
        Matrix matrix = new Matrix(matrixData);
        return matrix;
    }

}
