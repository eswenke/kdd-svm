package testing;
import static org.junit.jupiter.api.Assertions.assertEquals;

import evaluation.Metrics;
import org.junit.jupiter.api.Test;

public class MetricsTesting {


    @Test
    public void testAccuracy1() {
        double[] yTrue = {1, 2, 3, 4};
        double[] yPred = {1, 2, 3, 4};
        assertEquals(1.0, Metrics.accuracy(yTrue, yPred), 0);
    }
    @Test
    public void testAccuracy2() {
        double[] yTrue = {1, 2, 5, 4};
        double[] yPred = {1, 5, 2, 4};
        assertEquals(0.5, Metrics.accuracy(yTrue, yPred), 0);
    }
    @Test
    public void testAccuracy3() {
        double[] yTrue = {6, 7, 8, 9, 5};
        double[] yPred = {6, 6, 6, 6, 6};
        assertEquals(0.2, Metrics.accuracy(yTrue, yPred), 0);
    }

    @Test
    public void testPrecision1() {
        double[] yTrue = {1, 2, 3, 4};
        double[] yPred = {1, 2, 3, 4};
        assertEquals(1.0, Metrics.precision(yTrue, yPred, 1), 0);
    }
    @Test
    public void testPrecision2() {
        double[] yTrue = {1, 0, 0, 1};
        double[] yPred = {1, 1, 1, 1};
        assertEquals(0.5, Metrics.precision(yTrue, yPred, 1), 0);
    }
    @Test
    public void testPrecision3() {
        double[] yTrue = {1, 0, 1, 1, 1, 1, 1};
        double[] yPred = {0, 1, 1, 0, 1, 1, 0};
        assertEquals(0.75, Metrics.precision(yTrue, yPred, 1), 0);
    }
    @Test
    public void testRecall1() {
        double[] yTrue = {1, 1, 1, 1};
        double[] yPred = {1, 0, 0, 1};
        assertEquals(0.5, Metrics.recall(yTrue, yPred, 1), 0);
    }
    @Test
    public void testRecall2() {
        double[] yTrue = {0, 0, 0, 0};
        double[] yPred = {0, 1, 1, 0};
        assertEquals(0, Metrics.recall(yTrue, yPred, 1), 0);
    }
    @Test
    public void testRecall3() {
        double[] yTrue = {1, 0, 1, 1, 1, 1, 1};
        double[] yPred = {0, 1, 1, 0, 1, 1, 0};
        assertEquals(0.5, Metrics.recall(yTrue, yPred, 1), 0);
    }

    @Test
    public void testf1Score1() {
        double[] yTrue = {1, 1, 1, 1};
        double[] yPred = {1, 0, 0, 1};
        assertEquals(0.666666, Metrics.f1Score(yTrue, yPred, 1), .00001);
    }
    @Test
    public void testf1Score2() {
        double[] yTrue = {0, 0, 0, 0};
        double[] yPred = {0, 1, 1, 0};
        assertEquals(0, Metrics.f1Score(yTrue, yPred, 1), 0);
    }
    @Test
    public void testf1Score3() {
        double[] yTrue = {1, 0, 1, 1, 1, 1, 1};
        double[] yPred = {0, 1, 1, 0, 1, 1, 0};
        assertEquals(0.6, Metrics.f1Score(yTrue, yPred, 1), 0);
    }

    @Test
    public void confusionMatrix1() {
        double[] yTrue = {1, 1, 1, 1};
        double[] yPred = {1, 0, 0, 1};

        int[][] tmp = Metrics.confusionMatrix(yTrue, yPred, 1);
        //TN
        assertEquals(0, tmp[0][0] ,0);
        //FP
        assertEquals(0, tmp[0][1], 0);
        //FN
        assertEquals(2, tmp[1][0], 0);
        //TP
        assertEquals(2, tmp[1][1], 0);
    } //{TN, FP},
      //{FN, TP}
    @Test
    public void confusionMatrix2() {
        double[] yTrue = {0, 0, 0, 0};
        double[] yPred = {0, 1, 1, 0};
        int[][] tmp = Metrics.confusionMatrix(yTrue, yPred, 1);
        //TN
        assertEquals(2, tmp[0][0] ,0);
        //FP
        assertEquals(2, tmp[0][1], 0);
        //FN
        assertEquals(0, tmp[1][0], 0);
        //TP
        assertEquals(0, tmp[1][1], 0);
    }
    @Test
    public void confusionMatrix3() {
        double[] yTrue = {1, 0, 1, 1, 1, 1, 1};
        double[] yPred = {0, 1, 1, 0, 1, 1, 0};
        int[][] tmp = Metrics.confusionMatrix(yTrue, yPred, 1);
        //TN
        assertEquals(0, tmp[0][0] ,0);
        //FP
        assertEquals(1, tmp[0][1], 0);
        //FN
        assertEquals(3, tmp[1][0], 0);
        //TP
        assertEquals(3, tmp[1][1], 0);
    }
}