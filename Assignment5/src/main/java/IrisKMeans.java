import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class IrisKMeans {

    public static void main(String[] args) {
        int predictedVal[] = new int[150];
        try {
            // clustering
            DataSource src = new DataSource("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/iris.arff");
            Instances dt = src.getDataSet();
            SimpleKMeans model = new SimpleKMeans();
            model.setNumClusters(3);
            model.setPreserveInstancesOrder(true);
            model.buildClusterer(dt);
            System.out.println("model");
            System.out.println(model);
            // show labels per record
            int[] assignments = model.getAssignments();
            int i = 0;
            int count = 0;
            for (int clusterNum : assignments) {
//                System.out.printf("Instance %d [%s] -> Cluster %d \n", i, dt.get(i), clusterNum);
                predictedVal[count] = clusterNum;
                count++;
                i++;
            }
            // evaluation
            ClusterEvaluation eval = new ClusterEvaluation();
            DataSource valid = new DataSource("/Users/sneha/Projects/SER594/Assignment5/src/main/resources/iris.arff");
            Instances validI = valid.getDataSet();
            eval.setClusterer(model);
            eval.evaluateClusterer(validI);
//            System.out.println(eval.clusterResultsToString());
//            System.out.println(eval.getLogLikelihood());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        ConfusionMatrix matrix = new ConfusionMatrix(predictedVal);
    }
}