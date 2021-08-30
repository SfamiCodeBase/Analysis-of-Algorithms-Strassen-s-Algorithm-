import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WriteDataToCSV {

    public static void writeDataAtOnce(String filePath, HashMap<Integer, Long> averageRunningTimeData) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            List<String[]> data = new ArrayList<>();
            data.add(new String[] { "matrixSize", "averageRunningTime" });
            for (int matrixSize : averageRunningTimeData.keySet()){
                String size, averageRunningTime;
                size = Integer.toString(matrixSize);
                averageRunningTime = Long.toString(averageRunningTimeData.get(matrixSize));
                data.add(new String[] { size, averageRunningTime });
            }
            writer.writeAll(data);
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
