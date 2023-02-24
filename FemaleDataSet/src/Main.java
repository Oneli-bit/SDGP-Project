import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "BodyFat.txt";

        String line = "";

        FileWriter writer = new FileWriter("finalizeDataSet.csv");


        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while((line = br.readLine()) != null){
                String[] values = line.split("\t");
                System.out.println(Arrays.toString(values));
                writer.append(Arrays.toString(values));
                writer.append("\n");
            }

            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}