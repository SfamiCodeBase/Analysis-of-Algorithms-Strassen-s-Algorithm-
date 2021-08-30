import java.util.ArrayList;
import java.util.HashMap;

public class Simulations {

    private int inputUpperBound;
    private int numberOfSimulations;
    private ArrayList<Integer> matricesSizes;

    public Simulations(ArrayList<Integer> matricesSizes, int inputUpperBound, int numberOfSimulations){
        this.matricesSizes = matricesSizes;
        this.inputUpperBound = inputUpperBound;
        this.numberOfSimulations = numberOfSimulations;
    }

    public HashMap<Integer, Long> algorithmAverageRunningTime(String algorithm){
        HashMap<Integer, Long> averageDurations = new HashMap<>();
        HashMap<Integer, ArrayList<Long>> durations = runSimulation(algorithm);
        long averageDuration;

        for (int key : durations.keySet()){
            ArrayList<Long> matrixDurations = durations.get(key);
            long sumOfDurations = 0;

            for (long duration : matrixDurations){
                sumOfDurations += duration;
            }

            averageDuration = sumOfDurations / matrixDurations.size();
            averageDurations.put(key,averageDuration);
        }
        return averageDurations;
    }

    public HashMap<Integer, ArrayList<Long>> runSimulation(String algorithm){

        StrassensAlgorithm strassensAlgorithm = new StrassensAlgorithm();
        ArrayList<Integer> sizesOfMatrices = new ArrayList<>();
        HashMap<Integer, ArrayList<Long>> durations = new HashMap<>();
        System.out.println(algorithm);
        for (int i = 0; i < this.matricesSizes.size(); i++){
            ArrayList<Long> matricesOfSizeNDurations = new ArrayList<>();
            int sizeOfMatrix = this.matricesSizes.get(i);
            sizesOfMatrices.add(sizeOfMatrix);
            for (int j = 0; j < this.numberOfSimulations; j++){
                System.out.println(String.format("Running simulation %s for matrix of size %s ...", j, sizeOfMatrix));
                int[][] matrixA, matrixB , matrixC = null;
                long startTime, endTime, duration;

                matrixA = strassensAlgorithm.createMatrix(sizeOfMatrix, this.inputUpperBound);
                matrixB = strassensAlgorithm.createMatrix(sizeOfMatrix, this.inputUpperBound);
//                StrassensAlgorithm.printMatrix(matrixA,"matrixA " + algorithm);
//                StrassensAlgorithm.printMatrix(matrixB,"matrixB " + algorithm);

                startTime = System.currentTimeMillis();
                if (algorithm.equals("StrassenMethodSecond")) matrixC = strassensAlgorithm.StrassenMethodSecond(matrixA, matrixB);
                if (algorithm.equals("SquareMatrixMultiplyRecursive")) matrixC = strassensAlgorithm.SquareMatrixMultiplyRecursive(matrixA, matrixB);
                if (algorithm.equals("SquareMatrixMultiply")) matrixC = strassensAlgorithm.SquareMatrixMultiply(matrixA, matrixB);
                endTime = System.currentTimeMillis();
                duration = endTime - startTime;

                matricesOfSizeNDurations.add(duration);
//                StrassensAlgorithm.printMatrix(matrixC,"matrixC " + algorithm);
            }
            durations.put(sizeOfMatrix, matricesOfSizeNDurations);
        }
        return durations;
    }

    public void simulateAlgorithm(String algorithm, String filePath){
        HashMap<Integer, Long> averageDurations1 = algorithmAverageRunningTime(algorithm);
        WriteDataToCSV.writeDataAtOnce(filePath, averageDurations1);

    }
}
