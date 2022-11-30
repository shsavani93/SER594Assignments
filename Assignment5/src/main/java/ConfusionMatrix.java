import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfusionMatrix {
    public ConfusionMatrix(int[] predictedVal) {
        int falseZero = 0;
        int trueZero = 0;
        int trueOne = 0;
        int falseOne = 0;
        int falseTwo = 0;
        int trueTwo = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/iris_with_labels.arff"));
            String sc;
            //Total instances in iris dataset
            int[] totalValues = new int[150];
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
                } else if (predictedVal[i] == 2) {
                    if (predictedVal[i]== totalValues[i]) {
                        trueTwo++;
                    }
                    else {
                        falseTwo++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("############ Confusion Matrix ############");
        System.out.println(" 0 1 2");
        System.out.println("------");
        System.out.println(" " + trueZero + " " + falseOne + " " + falseZero + " | 0 = 0");
        System.out.println(" " + falseOne + " " + trueOne + " " + falseOne + " | 1 = 1");
        System.out.println(" " + falseTwo + " " + falseOne + " " + trueTwo + " | 2 = 2");
        System.out.println("#############################################");

        float accuracy = (float) (trueOne + trueZero + trueTwo) / 150;
        System.out.println("Accuracy: " +  accuracy);
    }
}
