import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {


    public static void main(String[] args){
        String filePath1 = "C:\\Users\\takal\\OneDrive\\Desktop\\Results\\simulationOneResults.csv";
        String algorithm1 = "SquareMatrixMultiply";
        String filePath2 = "C:\\Users\\takal\\OneDrive\\Desktop\\Results\\simulationTwoResults.csv";
        String algorithm2 = "SquareMatrixMultiplyRecursive";
        String filePath3 = "C:\\Users\\takal\\OneDrive\\Desktop\\Results\\simulationThreeResults.csv";
        String algorithm3 = "StrassenMethodSecond";

        int inputUpperBound = 10;
        int numberOfSimulations = 5;

        ArrayList<Integer> matricesSizes1 = new ArrayList<>(Arrays.asList(32,64,96,128,160,320,640,1000,2000));
        Simulations simulations;
        simulations = new Simulations(matricesSizes1,inputUpperBound,numberOfSimulations);

        simulations.simulateAlgorithm(algorithm1,filePath1);
        simulations.simulateAlgorithm(algorithm2,filePath2);
        simulations.simulateAlgorithm(algorithm1,filePath3);

    }

}
