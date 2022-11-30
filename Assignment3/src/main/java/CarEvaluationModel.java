import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.buffer.IntBuffer;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

/**
 * This class creates and train model for car evaluation. Loads the data from csv, create model and evaluate model.
 * @author      Sneha Savani
 * @author      Sandy
 */
public class CarEvaluationModel {

    public static void main(String[] args) throws IOException, InterruptedException {

        RecordReader recordReader = new CSVRecordReader(0, ',');

        recordReader.initialize(new FileSplit(new File("car.csv")));

        DataSetIterator trainData = new RecordReaderDataSetIterator(recordReader, 1728, 6, 4);

        RecordReader recordReaderTest = new CSVRecordReader(0, ',');

        recordReaderTest.initialize(new FileSplit(new File("car.csv")));

        DataSetIterator testData = new RecordReaderDataSetIterator(recordReaderTest, 1728, 6, 4);

        MultiLayerNetwork model = createModel(trainData);

        model.save(new File("car_evaluation_model"));

        MultiLayerNetwork createdModel = MultiLayerNetwork.load(new File("car_evaluation_model"), false);

        int[] values = {0,1,2,3,4,5};
        IntBuffer dataBuffer = new IntBuffer(values);
        INDArray input = Nd4j.zeros(1, 6);
        input.setData(dataBuffer);

        INDArray result = createdModel.output(input);
        System.out.println(result);
        // evaluation
        testModel(model, testData);
    }

    /**
     * This method iterates over testing data and prints evaluation statistics like confusion matrix,
     *  evaluation matrix.
     *
     * @param  model  multilayernetwork model
     * @param  testingData the testing data needed for evaluation
     * @return      null
     */
    private static void testModel(MultiLayerNetwork model, DataSetIterator testingData) {
        Evaluation eval = new Evaluation(4);
        while(testingData.hasNext()) {
            DataSet testCarData = testingData.next();
            INDArray predict2 = model.output(testCarData.getFeatures());
            eval.eval(testCarData.getLabels(), predict2);
        }
        System.out.println("evaluation");
        System.out.println (eval.stats());
    }

    /**
     * This method creates and train model using epochs.
     *
     * @param  train the training data used to train the model
     * @return      MultiLayerNetwork
     */
    private static MultiLayerNetwork createModel(DataSetIterator train) throws IOException {

        MultiLayerConfiguration cfg = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs ( 0.7, 0.0015))
                .list()
                .layer(0, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(6)
                        .nOut(20)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .activation(Activation.RELU)
                        .nIn(20)
                        .nOut(10)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(10)
                        .nOut(4)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.7);
        model.setListeners(new MyListener());
        model.fit(train, 4895);

        return model;
    }
}
// 0.9965 - 4895 - 0.02 error rate