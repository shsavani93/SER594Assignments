import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;

public class SalaryPredictionDecisionTree {

    public static void main(String[] args) {
        try {
            String path = "/Users/sneha/Downloads/salary.csv";
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(path));

            Instances dt = loader.getDataSet();
            dt.randomize(new java.util.Random(0));
            int trainDataSize = (int) (Math.round(dt.numInstances() * 0.66));
            int testDataSize = dt.numInstances() - trainDataSize;

            Instances trainInstances = new Instances(dt, 0, trainDataSize);
            Instances testInstances = new Instances(dt, trainDataSize, testDataSize);

            trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
            testInstances.setClassIndex(testInstances.numAttributes() - 1);

            J48 mytree = new J48();
            mytree.setUnpruned(true);
            mytree.buildClassifier(trainInstances);
            System.out.println(mytree);


            Evaluation evaluation = new Evaluation(trainInstances);
            evaluation.evaluateModel(mytree, testInstances);
            System.out.println(evaluation.toSummaryString());
            System.out.println(evaluation.toMatrixString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}