import java.util.ArrayList;
import java.util.Random;

public class OldCode {

    public void printMatrix(int[][] matrix, String name){
        System.out.println(name);
        int maxInt, maxNumberOfDigits;

        maxInt = 0;
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                if (ints[j] > maxInt) maxInt = ints[j];
            }
        }

        maxNumberOfDigits = Integer.toString(maxInt).length();
        for (int[] ints : matrix) {
            String row = "";
            for (int j = 0; j < matrix.length; j++) {
                int numberOfDigits = Integer.toString(ints[j]).length();
                String spaces = "";
                if (numberOfDigits < maxNumberOfDigits){
                    for (int i = 0; i < maxNumberOfDigits - numberOfDigits; i++)
                        spaces += ' ';
                    row += spaces + ints[j] + " ";
                }
                else {
                    row += ints[j] + " ";
                }
            }
            System.out.println(row);
        }
        System.out.println();
    }

    public int[][] createMatrix(int size, int inputUpperBound){
        Random random = new Random();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                int input = random.nextInt(inputUpperBound);
                matrix[i][j] = input;
            }
        }
        return matrix;
    }

    public int[][] SquareMatrixMultiply(int[][] A, int[][] B){
        /* This function implements the Strassen's algorithm for matrix multiplication */
        int size = A.length;
        int[][] C = new int[size][size];
        for (int row = 0; row < size; row++){
            for (int column = 0; column < size; column++){
                C[row][column] = 0;
                for (int k = 0; k < size; k++){
                    C[row][column] +=  A[row][k] * B[k][column];
                }
            }
        }
        return C;
    }

    public int[][] StrassenMethodSecond(int[][] A, int[][] B){
        /* This function implements the Strassen's algorithm for recursive matrix multiplication*/
        int n = A.length;
        int[][] C = new int[n][n];
        if (n == 1) C = SquareMatrixMultiply(A,B);
        else {
            int newSize = n / 2;
            int[][] a11 = new int[newSize][newSize];
            int[][] a12 = new int[newSize][newSize];
            int[][] a21 = new int[newSize][newSize];
            int[][] a22 = new int[newSize][newSize];

            int[][] b11 = new int[newSize][newSize];
            int[][] b12 = new int[newSize][newSize];
            int[][] b21 = new int[newSize][newSize];
            int[][] b22 = new int[newSize][newSize];

            // dividing the matrices in 4 sub-matrices:
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    a11[i][j] = A[i][j]; // top left
                    a12[i][j] = A[i][j + newSize]; // top right
                    a21[i][j] = A[i + newSize][j]; // bottom left
                    a22[i][j] = A[i + newSize][j + newSize]; // bottom right

                    b11[i][j] = B[i][j]; // top left
                    b12[i][j] = B[i][j + newSize]; // top right
                    b21[i][j] = B[i + newSize][j]; // bottom left
                    b22[i][j] = B[i + newSize][j + newSize]; // bottom right
                }
            }
            int[][] s1 = subtract(b12,b22);
            int[][] s2 = add(a11,a12);
            int[][] s3 = add(a21,a22);
            int[][] s4 = subtract(b21,b11);
            int[][] s5 = add(a11,a22);
            int[][] s6 = add(b11,b22);
            int[][] s7 = subtract(a12,a22);
            int[][] s8 = add(b21,b22);
            int[][] s9 = subtract(a11,a21);
            int[][] s10 = add(b11,b12);

            // Calculating p1 to p7:
            int[][] p1 = SquareMatrixMultiply(a11,s1);
            int[][] p2 = SquareMatrixMultiply(s2,b22);
            int[][] p3 = SquareMatrixMultiply(s3,b11);
            int[][] p4 = SquareMatrixMultiply(a22, s4);
            int[][] p5 = SquareMatrixMultiply(s5,s5);
            int[][] p6 = SquareMatrixMultiply(s7,s8);
            int[][] p7 = SquareMatrixMultiply(s9,s10);

            int[][] c11 = add(subtract(add(p5, p4),p2), p6);
            int[][] c12 = add(p1, p2);
            int[][] c21 = add(p3, p4);
            int[][] c22 = subtract(subtract(add(p5, p1), p3), p7);

            // Grouping the results obtained in a single matrix:
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    C[i][j] = c11[i][j];
                    C[i][j + newSize] = c12[i][j];
                    C[i + newSize][j] = c21[i][j];
                    C[i + newSize][j + newSize] = c22[i][j];
                }
            }

        }
        return C;
    }

    public int[][] SquareMatrixMultiplyRecursive(int[][] A, int[][] B){
        /* This function implements recursive square matrix multiplication */
        System.gc();
        int n = A.length;
        int[][] C = new int[n][n];
        if (n == 1) C = SquareMatrixMultiply(A,B);
        else {
            int newSize = n / 2;
            int[][] a11 = new int[newSize][newSize];
            int[][] a12 = new int[newSize][newSize];
            int[][] a21 = new int[newSize][newSize];
            int[][] a22 = new int[newSize][newSize];

            int[][] b11 = new int[newSize][newSize];
            int[][] b12 = new int[newSize][newSize];
            int[][] b21 = new int[newSize][newSize];
            int[][] b22 = new int[newSize][newSize];

            // dividing the matrices in 4 sub-matrices:
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    a11[i][j] = A[i][j]; // top left
                    a12[i][j] = A[i][j + newSize]; // top right
                    a21[i][j] = A[i + newSize][j]; // bottom left
                    a22[i][j] = A[i + newSize][j + newSize]; // bottom right

                    b11[i][j] = B[i][j]; // top left
                    b12[i][j] = B[i][j + newSize]; // top right
                    b21[i][j] = B[i + newSize][j]; // bottom left
                    b22[i][j] = B[i + newSize][j + newSize]; // bottom right
                }
            }
            int[][] c11 = add(SquareMatrixMultiplyRecursive(a11,b11) , SquareMatrixMultiplyRecursive(a12,b21));
            int[][] c12 = add(SquareMatrixMultiplyRecursive(a11,b12) , SquareMatrixMultiplyRecursive(a12,b22));
            int[][] c21 = add(SquareMatrixMultiplyRecursive(a21,b11) , SquareMatrixMultiplyRecursive(a22,b21));
            int[][] c22 = add(SquareMatrixMultiplyRecursive(a21,b12) , SquareMatrixMultiplyRecursive(a22,b22));

            // Grouping the results obtained in a single matrix:
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {

                    C[i][j] = c11[i][j];
                    C[i][j + newSize] = c12[i][j];
                    C[i + newSize][j] = c21[i][j];
                    C[i + newSize][j + newSize] = c22[i][j];
                }
            }

        }
        return C;
    }

    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    private static int nextPowerOfTwo(int n) {
        int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, log2);
    }


}
