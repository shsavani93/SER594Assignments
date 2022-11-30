import java.util.ArrayList;

public class BasicNeuralNetwork {

    static double LEARNING_RATE = 0.7;
    int nodesInputLayer;
    int nodesOutputLayer;
    int nodesHiddenLayer;
    double[] arrayValues;
    double[] arrayWeight;
    double[] arrayBias;

    public BasicNeuralNetwork (int input, int hidden, int output) {
        int totalNodes  = input + hidden + output;
        int totalWeights = (input * hidden) + (hidden * output);
        nodesInputLayer = input;
        nodesHiddenLayer = hidden;
        nodesOutputLayer = output;
        arrayValues = new double [totalNodes];
        arrayWeight = new double [totalWeights];
        arrayBias = new double [totalNodes];
        for (int i = 0; i < totalNodes; i++)
            arrayBias[i] = 0.5 - (Math.random());
        for (int i = 0; i < arrayWeight.length; i++)
            arrayWeight[i] = 0.5 - (Math.random());
    }

    private double sigmoid(double x) {
        return 1.0 / (1 + Math.exp(-1.0 * x));
    }

    public double forwardPropagation (double inputValues[]) {
        double result[] = new double[nodesOutputLayer];
        int currentNode = 0;
        int currentWeight = 0;

        ArrayList<Double> finalResult = new ArrayList();

        // process input layer
        for (int i = 0; i < nodesInputLayer; i++) {
            arrayValues[currentNode++] = inputValues[i];
        }
        // process hidden layer
        for (int i = 0; i < nodesHiddenLayer; i++) {
            double sum = arrayBias[currentNode];
            for (int j = 0; j < nodesInputLayer; j++) {
                sum += arrayValues[j] * arrayWeight[currentWeight++];
            }
            arrayValues[currentNode++] = sigmoid(sum);
        }
        // process output layer
        for (int i = 0; i < nodesOutputLayer; i++) {
            double sum = arrayBias[currentNode];
            for (int j = nodesInputLayer; j < nodesInputLayer + nodesHiddenLayer; j++) {
                sum += arrayValues[j] * arrayWeight[currentWeight++];
            }
            arrayValues[currentNode++] = result[i] = sigmoid(sum);
        }

        return result[0];
    }

    public void backPropagation(double[] actual) {
        double[] derivativeTotalWeight = new double [arrayWeight.length];
        double[] derivativeTotalBias = new double [arrayValues.length];
        double[] derivativeError = new double [arrayValues.length];
        double[] error = new double [arrayValues.length];
        int current;
        // outputs
        current = nodesInputLayer * nodesHiddenLayer;
        for (int i = (nodesInputLayer + nodesHiddenLayer); i < arrayValues.length; i++) {
            error[i] = actual[i - (nodesInputLayer + nodesHiddenLayer)] - arrayValues[i];
            derivativeError[i] = error[i] * arrayValues[i] * (1 - arrayValues[i]);
            for (int j = nodesInputLayer; j < (nodesInputLayer + nodesHiddenLayer); j++) {
                derivativeTotalWeight[current] = derivativeTotalWeight[current] + derivativeError[i] * arrayValues[j];
                error[j] = error[j] + arrayWeight[current] * derivativeError[i];
                current++;
            }
            derivativeTotalBias[i] = derivativeTotalBias[i] + derivativeError[i];
        }
        // hidden
        current = 0;
        for (int i = nodesInputLayer; i < (nodesInputLayer + nodesHiddenLayer); i++) {
            derivativeError[i] = error[i] * arrayValues[i] * (1 - arrayValues[i]);
        }
        for (int i = nodesInputLayer; i < (nodesInputLayer + nodesHiddenLayer); i++) {
            for (int j = 0; j < nodesInputLayer; j++) {
                derivativeTotalWeight[current] = derivativeTotalWeight[current] + derivativeError[i] * arrayValues[j];
                error[j] = error[j] + arrayWeight[current] * derivativeError[i];
                current++;
            }
            derivativeTotalBias[i] = derivativeTotalBias[i] + derivativeError[i];
        }
        // update weights
        for (int i = 0; i < arrayWeight.length; i++) {
            arrayWeight[i]  = arrayWeight[i]  +  (LEARNING_RATE * derivativeTotalWeight[i]);
            derivativeTotalWeight[i] = 0;
        }
        // update bias
        for (int i = nodesInputLayer; i < arrayValues.length; i++) {
            arrayBias[i]   = arrayBias[i] + (LEARNING_RATE * derivativeTotalBias[i]);
            derivativeTotalBias[i] = 0;
        }
    }

    public static void main(String[] args) {
        double[][] xorInput =  {{0.0, 0.0}, {1.0, 0.0}, {0.0, 1.0}, {1.0, 1.0}};
        double[][] xorOutput = {{0.0}, {1.0}, {1.0}, {0.0}};

        BasicNeuralNetwork network = new BasicNeuralNetwork(2, 3, 1);

        DataSet dataSet = new DataSet(xorInput, xorOutput);

        for (int iteration = 0; iteration < 10000; iteration++) {
            network.fit(dataSet, iteration);
        }

        double [] actualOutput = network.output(xorInput);

        System.out.println("################# Output array values ####################");
        for (double ans: actualOutput) {
            System.out.println(ans);
        }
        System.out.println("#########################################################");

        CustomEvaluation evaluation = new CustomEvaluation();
        evaluation.eval(xorOutput, actualOutput);
        evaluation.showStats();
    }

    public void fit(final DataSet dataSet, int iteration) {
        double [][] xorInput = dataSet.xorInput;
        double [][] xorOutPut = dataSet.xorOutput;
            double meanSquareError = 0;
            for (int currentInput = 0; currentInput < xorInput.length; currentInput++) {
                // forward
                this.forwardPropagation(xorInput[currentInput]);
                //  error
                final int outputIndex = this.nodesInputLayer + this.nodesHiddenLayer;
                for (int i = outputIndex; i < this.arrayValues.length; i++) {
                    double err = (xorOutPut[currentInput][i - outputIndex] - this.arrayValues[i]);
                    meanSquareError += Math.pow (err, 2)/2;
                }
                // back
                this.backPropagation(xorOutPut[currentInput]);
            }
            double err1 = meanSquareError / (this.nodesOutputLayer + this.nodesHiddenLayer-1);
//            System.out.println(iteration + " , " + Math.sqrt(err1));
    }

    public double [] output(final double[][] inputValues) {

        double [] output = new double[inputValues.length];
        for (int currentInput = 0; currentInput < inputValues.length; currentInput++) {
            // forward propogation
            output[currentInput] = this.forwardPropagation(inputValues[currentInput]);
        }

        return output;

    }

    static class DataSet {
        double [][] xorInput;
        double [][] xorOutput;

        public DataSet(double [][] xorInput, double[][] xorOutput) {
            this.xorInput = xorInput;
            this.xorOutput = xorOutput;
        }

    }
}