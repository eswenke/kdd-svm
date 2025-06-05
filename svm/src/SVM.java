// main SVM class in the default package
// no package declaration needed for the top-level src directory

import java.io.IOException;

import model.LinearKernel;
import model.SVMModel;
import model.Kernel;
import processing.CSVReader;
import processing.DataPreprocessor;
import processing.DataSplitter;

public class SVM {

    public static void main(String[] args) throws IOException {

        DataPreprocessor dp = new DataPreprocessor();


        //read csv
        double[][] data = CSVReader.readCSV("svm/src/data/dataset.csv"); // read some data

        //normalize and standardize data to decrease variance (stable), and standardize to eliviate outliers
        double[][] normalizedData = dp.normalize(data);
        double [][] standardizedData = dp.standardize(normalizedData);


        int labelCol = 11;

        //need to fix object so that is correct type?
        Object[] splitData = dp.splitFeaturesAndLabels(standardizedData, labelCol);
        //unsure how the type casting works sorry
        double[][] x = (double[][]) splitData[0];
        double[] y = (double[]) splitData[0];


        //split features and labels --> splits into 2d array and 1d array
        //2nd array = label and then we feed that to the training
        //split takes all the data many rows by 11 --> 10 features and 1 label

        DataSplitter ds = new DataSplitter(true, 0);
        int trainRatio = 1; //unsure of what this is supposed to be set to
        ds.splitTrainTest(x , y ,trainRatio);



        LinearKernel linearkernel = new LinearKernel(); //the kernel compares similiarity of input data to all the alphas
        double c = 0; //make new c
        double tolerance = 0; //make new tolerance
        int maxIterations = 0;
        SVMModel model = new SVMModel(linearkernel, c, tolerance, maxIterations);
        



        //svmModel
        //Give linear kernel and c and #iterations to model to svmModel which performs the training
        // run it for as many iterations as we feel
        //and find which #iterations is best (find over fit of under fit)
        //each time picks 2 data points and finds alphas for them and optimize them
                //alphas are confidence that we build margin for the hyper plan
        //continue to run through all points
        //every time alpha is updated we also update bias aka the y intercept of the hyperplane
        //at end of training optimizer has filled our alphas for each data point and a bias term
        //we can then give it any other points and it will predict them and say what side they are on the hyper plan





    }
}
