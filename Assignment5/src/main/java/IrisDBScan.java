import weka.clusterers.ClusterEvaluation;
import weka.clusterers.MakeDensityBasedClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class IrisDBScan {

    public static void main(String[] args) {
        try {
            // clustering
            DataSource src = new DataSource("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/iris.arff");
            Instances dt = src.getDataSet();
            // DBSCAN
            MakeDensityBasedClusterer model = new MakeDensityBasedClusterer();
            model.buildClusterer(dt);
            // evaluation
            ClusterEvaluation eval = new ClusterEvaluation();
            DataSource valid = new DataSource("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/irisTest.arff");
            Instances validI = valid.getDataSet();
            eval.setClusterer(model);
            eval.evaluateClusterer(validI);
            System.out.println(eval.clusterResultsToString());
            System.out.println(eval.getLogLikelihood());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

