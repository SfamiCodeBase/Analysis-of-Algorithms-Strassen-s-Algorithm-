import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void simulateAlgorithm(String algorithm, ArrayList<Integer> matricesSizes,int inputUpperBound,int numberOfSimulations, String filePath){

        Simulations simulations = new Simulations(matricesSizes, inputUpperBound, numberOfSimulations);
        HashMap<Integer, Long> averageDurations1 = simulations.algorithmAverageRunningTime(algorithm);
        WriteDataToCSV.writeDataAtOnce(filePath, averageDurations1);

    }

    public static void main(String[] args){

        String filePath1 = "C:\\Users\\takal\\OneDrive\\Desktop\\Results\\simulationOneResults.csv";
        String algorithm1 = "SquareMatrixMultiply";
        ArrayList<Integer> matricesSizes1 = new ArrayList<>(Arrays.asList(1,100,300,400,500,700,1000));
        simulateAlgorithm(algorithm1, matricesSizes1,10,10,filePath1);
        String filePath2 = "C:\\Users\\takal\\OneDrive\\Desktop\\Results\\simulationTwoResults.csv";
        String algorithm2 = "SquareMatrixMultiplyRecursive";
        ArrayList<Integer> matricesSizes2 = new ArrayList<>(Arrays.asList(1,100,300,400,500,700,1000));
        simulateAlgorithm(algorithm2, matricesSizes2,5,5,filePath2);

    }

}
