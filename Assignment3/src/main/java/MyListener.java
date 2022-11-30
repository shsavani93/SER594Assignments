import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import java.util.List;
import java.util.Map;

public class MyListener implements TrainingListener {
    private int counter;

    @Override
    public void iterationDone(Model model, int iteration, int epoch) {
        counter++;
        System.out.println(counter + " listening: " + model.score());
    }

    @Override
    public void onEpochStart(Model model) {}

    @Override
    public void onEpochEnd(Model model) {}

    @Override
    public void onForwardPass(Model model, List<INDArray> activations) {}

    @Override
    public void onForwardPass(Model model, Map<String, INDArray> activations) {}

    @Override
    public void onGradientCalculation(Model model) {}

    @Override
    public void onBackwardPass(Model model) {}
}