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

public class GoodIRISDataSetWithDL4J {

    public static void main(String[] args)  {

        DataSetIterator train = new IrisDataSetIterator(100, 100);
        DataSetIterator test = new IrisDataSetIterator(100, 50);


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
                .weightInit(WeightInit.XAVIER)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .activation(Activation.SOFTMAX)
                        .nIn(4)
                        .nOut(2)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(2)
                        .nOut(3)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.7);
        System.out.println(model.summary());

        for (int i = 0; i < 100; i++) {
            System.out.print(".");
            model.setListeners(new MyListener());
            model.fit(train, 5);
        }

        return model;
    }
}
