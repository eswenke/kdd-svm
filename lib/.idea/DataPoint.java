package your.package;

public class DataPoint {
    private double[] features;
    private int label; // 1 for positive class, -1 for negative class

    // constructor for a data point with 3 features assuming last is the label
    public DataPoint(double feature1, double feature2, double feature3) {
        // Assuming the third value is the class label
        this.features = new double[] { feature1, feature2 };
        this.label = (int) feature3 > 0 ? 1 : -1; // Convert to binary classification labels
    }

    // datapoint constructor with arbitrary features
    public DataPoint(double[] features, int label) {
        this.features = features;
        this.label = label;
    }

    // return the features
    public double[] getFeatures() {
        return features;
    }

    // get the class label
    public int getLabel() {
        return label;
    }

    // get specific value of feature given index
    public double getFeature(int index) {
        return features[index];
    }

    // string repr of data point
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataPoint[features=(");
        for (int i = 0; i < features.length; i++) {
            sb.append(features[i]);
            if (i < features.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("), label=").append(label).append("]");
        return sb.toString();
    }
}
