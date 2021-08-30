import java.util.ArrayList;
import java.util.Random;

public class StrassensAlgorithm {

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

    public int[][] SquareMatrixMultiplyRecursive(int[][] A, int[][] B){
        /* This function implements the Strassen's algorithm for recursive matrix multiplication*/
        System.gc();
        int n = A.length;
        int[][] C = new int[n][n];
        if (n <= 1) C = SquareMatrixMultiply(A,B);
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
            // Calculating p1 to p7:
            int[][] aResult = add(a11, a22);
            int[][] bResult = add(b11, b22);
            int[][] p1 = SquareMatrixMultiplyRecursive(aResult, bResult);
            // p1 = (a11+a22) * (b11+b22)

            aResult = add(a21, a22); // a21 + a22
            int[][] p2 = SquareMatrixMultiplyRecursive(aResult, b11); // p2 = (a21+a22) * (b11)

            bResult = subtract(b12, b22); // b12 - b22
            int[][] p3 = SquareMatrixMultiplyRecursive(a11, bResult);
            // p3 = (a11) * (b12 - b22)

            bResult = subtract(b21, b11); // b21 - b11
            int[][] p4 = SquareMatrixMultiplyRecursive(a22, bResult);
            // p4 = (a22) * (b21 - b11)

            aResult = add(a11, a12); // a11 + a12
            int[][] p5 = SquareMatrixMultiplyRecursive(aResult, b22);
            // p5 = (a11+a12) * (b22)

            aResult = subtract(a21, a11); // a21 - a11
            bResult = add(b11, b12); // b11 + b12
            int[][] p6 = SquareMatrixMultiplyRecursive(aResult, bResult);
            // p6 = (a21-a11) * (b11+b12)

            aResult = subtract(a12, a22); // a12 - a22
            bResult = add(b21, b22); // b21 + b22
            int[][] p7 = SquareMatrixMultiplyRecursive(aResult, bResult);
            // p7 = (a12-a22) * (b21+b22)

            // calculating c21, c21, c11 e c22:
            int[][] c12 = add(p3, p5); // c12 = p3 + p5
            int[][] c21 = add(p2, p4); // c21 = p2 + p4

            aResult = add(p1, p4); // p1 + p4
            bResult = add(aResult, p7); // p1 + p4 + p7
            int[][] c11 = subtract(bResult, p5);
            // c11 = p1 + p4 - p5 + p7

            aResult = add(p1, p3); // p1 + p3
            bResult = add(aResult, p6); // p1 + p3 + p6
            int[][] c22 = subtract(bResult, p2); // c22 = p1 + p3 - p2 + p6

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

    public int[][] strassen(ArrayList<ArrayList<Integer>> A,
                                   ArrayList<ArrayList<Integer>> B) {
        /* Make the matrices bigger so that you can apply the strassen
           algorithm recursively without having to deal with odd
           matrix sizes */
        int n = A.size();
        int m = nextPowerOfTwo(n);
        int[][] APrep = new int[m][m];
        int[][] BPrep = new int[m][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                APrep[i][j] = A.get(i).get(j);
                BPrep[i][j] = B.get(i).get(j);
            }
        }

        int[][] CPrep = SquareMatrixMultiplyRecursive(APrep, BPrep);
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = CPrep[i][j];
            }
        }
        return C;
    }
}
