package bilm463proje.server;

import java.io.Serializable;

/**
 * Matrix' i ifade eden sınıf
 *
 * @author 07051102,07051124,07050941
 */
public class Matrix implements Serializable {

    private double[][] matrix;
    private int rowLength;
    private int columnLength;

    /**
     * İçi boş matrix oluşturulmak istendiğinde kullanılacak constructor
     *
     * @param rowLength satır büyüklüğü
     * @param columnLength sütun büyüklüğü
     */
    public Matrix(int rowLength, int columnLength) {
        this.rowLength = rowLength;
        this.columnLength = columnLength;
        matrix = new double[rowLength][columnLength];
    }

    /**
     * Hazır data olduğunda kullanılacak constructor
     *
     * @param matrix matrix datası
     */
    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        rowLength = matrix.length;
        columnLength = matrix[0].length;
    }

    /**
     * Matrix'in bir hücresini set etmek istediğinde kullanılacak fonksiton
     *
     * @param rowIndex set edilmek istenen satır
     * @param columnIndex set edilmek istenen sütun
     * @param value set edilmek istenen değer
     */
    public void setPoint(int rowIndex, int columnIndex, double value) {
        matrix[rowIndex][columnIndex] = value;
    }

    /**
     * Matrix'in indexi verilen satırını döndürür
     *
     * @param index istenilen satır indeksi
     * @return istenilen satır
     */
    public double[] getRow(int index) {
        double[] array = new double[matrix[index].length];
        System.arraycopy(matrix[index], 0, array, 0, array.length);
        return array;
    }

    /**
     * Matrix'in indexi verilen sütunu döndürür
     *
     * @param index istenilen sütun indeksi
     * @return istenilen sütun
     */
    public double[] getColumn(int index) {
        double[] array = new double[matrix.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = matrix[i][index];
        }
        return array;
    }

    /**
     *
     * @return sütun büyüklüğü
     */
    public int getColumnLength() {
        return columnLength;
    }

    /**
     *
     * @return satır büyüklüğü
     */
    public int getRowLength() {
        return rowLength;
    }

    /**
     * tutulan 2 boyutlu matrix arrayi
     *
     * @return 2 boyutlu double[][] matrix arrayini döndürür
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     *
     * @return Matrix'in string olarak ifadesi döndürür
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                s += String.valueOf(matrix[i][j]) + " ";
            }
            s += "\n";
        }
        return s;
    }

}
