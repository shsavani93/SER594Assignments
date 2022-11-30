import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.evaluation.classification.Evaluation;

public class MNISTDataSet {

    public static void main(String[] args) throws Exception {

        DataSetIterator train =
                new MnistDataSetIterator(100, 60000, true);
        DataSetIterator test =
                new MnistDataSetIterator(100, 10000, true);
        MultiLayerConfiguration cfg =
                new NeuralNetConfiguration.Builder()
                        .weightInit(WeightInit.XAVIER)
                        .updater(new Adam(1e-3))
                        .list()
                        .layer(new ConvolutionLayer.Builder(5, 5)
                                .nIn(1)
                                .stride(1, 1)
                                .nOut(20)
                                .activation(Activation.IDENTITY)
                                .build())
                        .layer(new SubsamplingLayer.Builder(PoolingType.MAX)
                                .kernelSize(2, 2)
                                .stride(2, 2)
                                .build())
                        .layer(new ConvolutionLayer.Builder(5, 5)
                                .stride(1, 1)
                                .nOut(50)
                                .activation(Activation.IDENTITY)
                                .build())
                        .layer(new SubsamplingLayer.Builder(PoolingType.MAX)
                                .kernelSize(2, 2)
                                .stride(2, 2)
                                .build())
                        .layer(new DenseLayer.Builder().activation(Activation.RELU)
                                .nOut(500).build())
                        .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                .nOut(10)
                                .activation(Activation.SOFTMAX)
                                .build())
                        .setInputType(InputType.convolutionalFlat(28, 28, 1))
                        .build();


        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.7);
        // training
        for (int i = 0; i < 100; i++) {
            System.out.print(".");
            model.fit(train);
        }
        // evaluation
        Evaluation eval = new Evaluation(10);
        while (test.hasNext()) {
            DataSet testMnist = test.next();
            INDArray predict2 = model.output(testMnist.getFeatures());
            eval.eval(testMnist.getLabels(), predict2);
        }
        System.out.println(eval.stats());
    }

}