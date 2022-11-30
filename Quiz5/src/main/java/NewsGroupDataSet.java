import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.topics.MarginalProbEstimator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.InstanceList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class NewsGroupDataSet {

    public static void main(String[] args) throws IOException {
        File[] directories = new File[1];
        directories[0] = new File("src/main/resources/20_newsgroups");
        FileIterator iterator = new FileIterator(directories,  FileIterator.LAST_DIRECTORY);

        // pre processing pipeline
        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
        pipeList.add(new Input2CharSequence("UTF-8"));
        Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
        pipeList.add(new CharSequence2TokenSequence(tokenPattern));
        pipeList.add(new TokenSequenceLowercase());
        pipeList.add(new TokenSequenceRemoveStopwords(false));
        pipeList.add(new TokenSequence2FeatureSequence());
        SerialPipes pipelines = new SerialPipes(pipeList);
        InstanceList instances = new InstanceList(pipelines);
        instances.addThruPipe(iterator);
        // simple threaded LDA model
        ParallelTopicModel model = new ParallelTopicModel(20,0.01,0.01);
        // Split dataset
        InstanceList[] instanceSplit = instances.split(new double[] {0.9,0.1,0.0});
        model.addInstances(instanceSplit[0]);
        model.setNumThreads(4);
        model.setNumIterations(2000);
        model.estimate();
        // Get Estimator
        MarginalProbEstimator estimator = model.getProbEstimator();
        double loglike = estimator.evaluateLeftToRight(instanceSplit[1], 20, false, null);
        System.out.println("LogLike: " + loglike);
    }
}
