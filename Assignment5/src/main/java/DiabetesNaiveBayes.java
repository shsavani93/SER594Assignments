import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DiabetesNaiveBayes {

    public static void main(String[] args) {
        try {
            DataSource src = new DataSource("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/diabetes.arff");
            Instances dt = src.getDataSet();
            dt.randomize(new java.util.Random(0));
            int trainDataSize = (int) (Math.round(dt.numInstances() * 0.66));
            int testDataSize = dt.numInstances() - trainDataSize;

            Instances trainInstances = new Instances(dt, 0, trainDataSize);
            Instances testInstances = new Instances(dt, trainDataSize, testDataSize);

            trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
            testInstances.setClassIndex(testInstances.numAttributes() - 1);

            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.buildClassifier(trainInstances);
            System.out.println(naiveBayes);

            Evaluation evaluation = new Evaluation(trainInstances);
            evaluation.evaluateModel(naiveBayes, testInstances);
            System.out.println(evaluation.toSummaryString());
            System.out.println(evaluation.toMatrixString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}