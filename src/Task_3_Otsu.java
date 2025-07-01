import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;


import java.awt.*;

public class Task_3_Otsu implements PlugInFilter  {

    final private int HISTOGRAM_LENGTH = 256;

    public static void main(String[] args) {

        new ij.ImageJ();
        // Open an image or use the current one
        ImagePlus imp = IJ.openImage();

        Task_1_Threshold task1Threshold = new Task_1_Threshold();
        task1Threshold.setup("", imp);
        task1Threshold.correctIllumination(imp.getProcessor());

        // Create and run the plugin
        Task_3_Otsu otsu = new Task_3_Otsu();
        int setupResult = otsu.setup("", imp);

        if ((setupResult & PlugInFilter.DOES_8G) == 0) {
            IJ.showMessage("Error", "This plugin requires an 8-bit grayscale image");
            return;
        }

        otsu.run(imp.getProcessor());
    }


    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor ip) {
        ByteProcessor segmented = otsuSegmentation(ip);
        new ImagePlus("Otsu Segmentation", segmented).show();
    }

    public double[] getHistogram(ImageProcessor in) {
        double[] histogram = new double[HISTOGRAM_LENGTH];
        int width = in.getWidth();
        int height = in.getHeight();
        int totalPixels = width * height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int value = in.getPixel(i, j);
                histogram[value]++;
            }
        }

        for (int i = 0; i < HISTOGRAM_LENGTH; i++) {
            histogram[i] /= totalPixels;
        }

        return histogram;
    }

    public double[] getP1(double[] histogram) {
        double[] P1 = new double[HISTOGRAM_LENGTH];
        P1[0] = histogram[0];
        for (int i = 1; i < HISTOGRAM_LENGTH; i++) {
            P1[i] = P1[i-1] + histogram[i];
        }
        return P1;
    }

    public double[] getP2(double[] P1) {
        double[] P2 = new double[HISTOGRAM_LENGTH];
        for (int i = 0; i < HISTOGRAM_LENGTH; i++) {
            P2[i] = 1.0 - P1[i];
        }
        return P2;
    }

    public double[] getMu1(double[] histogram, double[] P1) {
        double[] mu1 = new double[HISTOGRAM_LENGTH];
        double sum = 0;

        for (int i = 0; i < HISTOGRAM_LENGTH; i++) {

            sum += (i + 1) * histogram[i];

//            mu1[i] = (P1[i] > 1e-10) ? sum / P1[i] : i;

            if (P1[i] <= 0) {
                mu1[i] = sum / 1e-10;
            } else {
                mu1[i] = sum / P1[i];
            }
        }
        return mu1;
    }


    public double[] getMu2(double[] histogram, double[] P2) {
        double[] mu2 = new double[HISTOGRAM_LENGTH];
        double totalSum = 0;
        double currentSum = 0;

        // Calculate total sum of (i+1)*h(i)
        for (int i = 0; i < HISTOGRAM_LENGTH; i++) {
//            totalSum += (i + 1) * histogram[i];

            currentSum += (i + 1) * histogram[i];

//            mu2[i] = (P2[i] > 1e-10) ? (totalSum - currentSum) / P2[i] : i;
            if (P2[i] <= 0) {
                mu2[i] = (currentSum) / 1e-10;
            } else {
                mu2[i] = (currentSum) / P2[i];
            }
        }

        return mu2;
    }


    public double[] getSigmas(double[] P1, double[] P2, double[] mu1, double[] mu2) {
        double[] sigmas = new double[HISTOGRAM_LENGTH];

        for (int i = 0; i < HISTOGRAM_LENGTH; i++) {
//            sigmas[i] = P1[i] * P2[i] * Math.pow(mu1[i] - mu2[i], 2);
//            sigmas[i] = Math.sqrt(sigmas[i]);

            sigmas[i] = P1[i] * P2[i] * Math.pow(mu1[i] - mu2[i], 2);
        }

        return sigmas;
    }

    public int getMaximum(double[] sigmas) {
        if (sigmas == null || sigmas.length == 0) {
            return 0; // Return default threshold if array is empty
        }

        int maxIndex = 0;
        double maxValue = sigmas[0];

        // Find the index of maximum sigma value
        for (int i = 1; i < sigmas.length; i++) {
            // Use >= to get the last occurrence if there are multiple maxima
            if (sigmas[i] >= maxValue) {
                maxValue = sigmas[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public ByteProcessor otsuSegmentation(ImageProcessor ip) {

//        Task_1_Threshold task1Threshold = new Task_1_Threshold();
//        task1Threshold.correctIllumination(ip);

        double[] histogram = getHistogram(ip);

        double[] P1 = getP1(histogram);
        double[] P2 = getP2(P1);

        double[] mu1 = getMu1(histogram, P1);
        double[] mu2 = getMu2(histogram, P2);

        double[] sigmas = getSigmas(P1, P2, mu1, mu2);

        int threshold = getMaximum(sigmas);

        ByteProcessor result = new ByteProcessor(ip.getWidth(), ip.getHeight());
        for (int x = 0; x < ip.getWidth(); x++) {
            for (int y = 0; y < ip.getHeight(); y++) {
                int value = ip.getPixel(x, y) >= threshold ? 255 : 0;
                result.set(x, y, value);
            }
        }

        return result;
    }



}
