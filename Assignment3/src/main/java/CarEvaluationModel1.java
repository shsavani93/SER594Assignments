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
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

public class CarEvaluationModel1 {

    public static void main(String[] args) throws IOException, InterruptedException {

        RecordReader recordReader = new CSVRecordReader(0, ',');

        recordReader.initialize(new FileSplit(new File("car.csv")));

        DataSetIterator trainData = new RecordReaderDataSetIterator(recordReader, 1728, 6, 4);

        RecordReader recordReaderTest = new CSVRecordReader(0, ',');

        recordReaderTest.initialize(new FileSplit(new File("car.csv")));

        DataSetIterator testData = new RecordReaderDataSetIterator(recordReaderTest, 1728, 6, 4);

        MultiLayerNetwork model = createModel(trainData);

        // evaluation
        testModel(model, testData);
    }

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

    private static MultiLayerNetwork createModel(DataSetIterator train) {

        MultiLayerConfiguration cfg = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs ( 0.7, 0.0015))
//                .updater(new Nadam())
                .list()
                .layer(0, new DenseLayer.Builder()
                        .activation(Activation.SIGMOID)
                        .nIn(6)
                        .nOut(25)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .activation(Activation.RELU)
                        .nIn(25)
                        .nOut(15)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(15)
                        .nOut(4)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.7);
        model.setListeners(new MyListener());
        model.fit(train, 4390);

        return model;
    }
}