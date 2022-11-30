import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.buffer.IntBuffer;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * @author Venkata Surya Shandilya Kambhampati, ASU ID 1219412519
 * @author Sneha Savani ASU ID: 1220549876
 * <p>
 * This class handles the ML model related tasks. Loading a model, running classification using the model and others
 * are the activities involved in this.
 */
public class Model {
    MultiLayerNetwork model;

    /**
     * This method loads the model from the environment.
     *
     * @throws IOException If the file to be loaded is not found in the environment.
     */

    public void model() throws IOException {

        model = MultiLayerNetwork.load(new File("car_evaluation_model"), false);
    }

    /**
     * This method is called to get the classification results by using the model.
     *
     * @param values The input values to the model.
     * @return The highest value among the predicted values by the model.
     */
    public int getOutput(int[] values) {
        IntBuffer dataBuffer = new IntBuffer(values);
        INDArray input = Nd4j.zeros(1, 6);
        input.setData(dataBuffer);
        INDArray result = model.output(input);
        int max = result.getInt(0);
        for (int i = 1; i <= 3; i++) {
            if (result.getFloat(i) > result.getFloat(i - 1)) {
                max = result.getInt(i);
            }
        }
        System.out.println(max);
        System.out.println(result);
        return max;
    }
}
