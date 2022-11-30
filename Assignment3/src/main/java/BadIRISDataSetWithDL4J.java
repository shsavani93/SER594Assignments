import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class BadIRISDataSetWithDL4J {

    public static void main(String[] args)  {

        DataSetIterator train = new IrisDataSetIterator(150, 100);
        DataSetIterator test = new IrisDataSetIterator(150, 50);

        MultiLayerNetwork model = createModel(train);

        // evaluation
        testModel(model, test);

    }

    private static void testModel(MultiLayerNetwork model, DataSetIterator testingData) {
        Evaluation eval = new Evaluation();
        while(testingData.hasNext()) {
            DataSet testIRIS = testingData.next();
            INDArray predict2 = model.output(testIRIS.getFeatures());
            eval.eval(testIRIS.getLabels(), predict2);
        }
        System.out.println (eval.stats());
    }

    private static MultiLayerNetwork createModel(DataSetIterator train) {

        MultiLayerConfiguration cfg = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.UNIFORM)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(4)
                        .nOut(10)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .activation(Activation.RELU)
                        .nIn(10)
                        .nOut(5)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.SIGMOID)
                        .nIn(5)
                        .nOut(3)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.1);

        for (int i = 0; i < 100; i++) {
            System.out.print(".");
            model.setListeners(new MyListener());
            model.fit(train, 2);
        }

        return model;
    }
}
