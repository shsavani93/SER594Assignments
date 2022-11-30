import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LaborConfusionMatrix {
    public LaborConfusionMatrix(int[] predictedVal) {
        int falseZero = 0;
        int trueZero = 0;
        int trueOne = 0;
        int falseOne = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/labor_with_labels.arff"));
            String sc;
            //Total instances in labor dataset
            int[] totalValues = new int[57];
            int count = 0;
            while((sc = br.readLine()) != null) {
                totalValues[count] = Integer.parseInt(sc.substring(sc.length() -1, sc.length()));
                count++;
            }

            for(int i = 0; i < totalValues.length; i++) {
                if (predictedVal[i] == 0) {
                    if (predictedVal[i] == totalValues[i]) {
                        trueZero++;
                    }
                    else {
                        falseZero++;
                    }
                }
                else if (predictedVal[i] == 1) {
                    if (predictedVal[i]== totalValues[i]) {
                        trueOne++;
                    }
                    else {
                        falseOne++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("############ Confusion Matrix ############");
        System.out.println(" 0 1");
        System.out.println("------");
        System.out.println(" " + trueZero +  " " + falseZero + " | 0 = 0");
        System.out.println(" " + falseOne + " " + trueOne  + " | 1 = 1");
        System.out.println("#############################################");

        float accuracy = (float) (trueOne + trueZero) / 57;
        System.out.println("Accuracy: " +  accuracy);
    }
}
