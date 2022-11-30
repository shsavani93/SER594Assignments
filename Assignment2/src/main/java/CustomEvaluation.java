import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomEvaluation {
    double truePositive = 0.0;
    double trueNegative = 0.0;
    double falsePositive = 0.0;
    double falseNegative = 0.0;
    double precision;
    double accuracy;
    double f1Score;
    double recall;

    // To run custom evaluation class
    public static void main(String [] args) {
        double [][] predictedValues = {{0.0}, {1.0}, {1.0}, {0.0}};
        double [] actualValues = {0.0, 0.9, 0.9, 0.0};
        CustomEvaluation eval = new CustomEvaluation();
        eval.eval(predictedValues, actualValues);
    }

    public void eval(double[][] predictedValues, double[] actualValues) {

        // Calculate TP, FN, FP, TN values for confusion matrix
       createConfusionMatrix(predictedValues, actualValues);

       // Accuracy = TP + TN / TP + TN + FP + FN
       accuracy = (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);

       // Precision = TP / TP + FP
       precision =  truePositive / (truePositive + falsePositive);

       // Recall  = TP / TP + FN
       recall = truePositive / (truePositive + falseNegative);

       // F1 Score = 2 * Precision * Recall / Precision + Recall
       f1Score = (2 * precision * recall) / (precision + recall);
    }

    private void createConfusionMatrix(double[][] predictedValues, double[] actualValues) {
        for (int i = 0; i < predictedValues.length; i++) {

            BigDecimal bdPredict = new BigDecimal(predictedValues[i][0]).setScale(1, RoundingMode.DOWN);
            double predictedValue = bdPredict.doubleValue();

            BigDecimal bdActual = new BigDecimal(actualValues[i]).setScale(1, RoundingMode.DOWN);
            double actualValue = bdActual.doubleValue();

            if (predictedValue == 0.0) {
                if (predictedValue == actualValue) {
                    truePositive++;
                }
                else {
                    falsePositive++;
                }
            }
            else if (predictedValue == 1.0) {
                if (predictedValue == actualValue) {
                    falseNegative++;
                }
                else {
                    trueNegative++;
                }
            }
        }
        System.out.println("TruePositive: " + truePositive+ "\n"+ "FalsePositive: " + falsePositive+ "\n" + "FalseNegative: " + falseNegative + "\n" +  "TrueNegative: " + trueNegative);
    }

    public void showStats(){
        System.out.println("############ Evaluation Matrix ############");
        System.out.println("Accuracy:     " + accuracy);
        System.out.println("Precision:    " + precision);
        System.out.println("Recall:       " + recall);
        System.out.println("F1 Score:     " + f1Score);
        System.out.println("############ Confusion Matrix ############");
        System.out.println(" 0  1 ");
        System.out.println("------");
        System.out.println(" " + truePositive + "  " + falsePositive + "  | 0 = 0");
        System.out.println(" " + falseNegative + "  " + trueNegative + "  | 1 = 1");
        System.out.println("#############################################");
    }

}
