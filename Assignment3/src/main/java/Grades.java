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
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

/**
 * This class creates and train model for car evaluation. Loads the data from csv, create model and evaluate model.
 *
 * @author Sneha Savani
 * @author Sandy
 */
public class Grades {

    public static void main(String[] args) throws IOException, InterruptedException {

        RecordReader recordReader = new CSVRecordReader(0, ',');

        recordReader.initialize(new FileSplit(new File("GradesFinal.csv")));

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 159, 15, 4);

        DataSet allData = iterator.next();
        allData.shuffle(42);

        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.7);

        DataSet train = testAndTrain.getTrain();
        DataSet test = testAndTrain.getTest();
        MultiLayerNetwork model = createModel(train);

        testModel(model, test);
    }

    /**
     * This method iterates over testing data and prints evaluation statistics like confusion matrix,
     * evaluation matrix.
     *
     * @param model    multi layernetwork model
     * @param testData the testing data needed for evaluation
     * @return null
     */
    private static void testModel(MultiLayerNetwork model, DataSet testData) {
        Evaluation eval = new Evaluation(4);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        System.out.println(eval.stats());
    }

    /**
     * This method creates and train model using epochs.
     *
     * @param train the training data used to train the model
     * @return MultiLayerNetwork
     */
    private static MultiLayerNetwork createModel(DataSet train) {

        MultiLayerConfiguration cfg = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.UNIFORM)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(15)
                        .nOut(12)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(12)
                        .nOut(10)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(10)
                        .nOut(8)
                        .build())
                .layer(3, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(8)
                        .nOut(6)
                        .build())
                .layer(4, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.SIGMOID)
                        .nIn(6)
                        .nOut(4)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.7);

        for (int i = 0; i < 400000; i++)
            model.fit(train);

        return model;
    }
}