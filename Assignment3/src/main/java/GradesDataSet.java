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
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

public class GradesDataSet {

    public static void main(String[] args) throws IOException, InterruptedException {

        RecordReader recordReader = new CSVRecordReader(0, ',');

        recordReader.initialize(new FileSplit(new File("grades_dataset.txt")));

        DataSetIterator trainData = new RecordReaderDataSetIterator(recordReader, 159, 15, 4);

        RecordReader recordReaderTest = new CSVRecordReader(0, ',');

        recordReaderTest.initialize(new FileSplit(new File("grades_dataset.txt")));

        DataSetIterator testData = new RecordReaderDataSetIterator(recordReaderTest, 159, 15, 4);

        MultiLayerNetwork model = createModel(trainData);

        // evaluation
        testModel(model, testData);

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
                        .activation(Activation.RELU)
                        .nIn(15)
                        .nOut(17)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(17)
                        .nOut(4)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(cfg);
        model.init();
        model.setLearningRate(0.7);

        for (int i = 0; i < 100; i++) {
            System.out.print(".");
            model.setListeners(new MyListener());
            model.fit(train, 100);
        }

        return model;
    }
}
