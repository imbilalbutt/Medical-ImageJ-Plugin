import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


public class Task_1_Threshold implements PlugInFilter {
    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }


    public ByteProcessor threshold ( ImageProcessor ip , int threshold ){
        ByteProcessor bp = new ByteProcessor(ip.getWidth(), ip.getHeight());

        for (int x = 0; x < ip.getWidth(); x++){
            for (int y = 0; y < ip.getHeight(); y++){
                if (ip.getPixel(x,y) >= threshold) {
                    bp.set(x, y, 255); // White
                } else {
                    bp.set(x, y, 0);   // Black
                }
            }
        }
        return bp;
    }

    public ByteProcessor correctIllumination ( ImageProcessor ip ){

        FloatProcessor originalFP = ip.convertToFloatProcessor();
        FloatProcessor blurredFP = ip.convertToFloatProcessor();

        blurredFP.blurGaussian(75);

        FloatProcessor correctedFP = new FloatProcessor(ip.getWidth(), ip.getHeight());

        float[] originalPixels = (float[])originalFP.getPixels();
        float[] blurredPixels = (float[])blurredFP.getPixels();
        float[] correctedPixels = (float[])correctedFP.getPixels();

        for (int i = 0; i < originalPixels.length; i++) {
            // Avoid division by zero and maintain proper dynamic range
            correctedPixels[i] = blurredPixels[i] != 0 ? originalPixels[i] / blurredPixels[i] : 0;
        }

        return correctedFP.convertToByteProcessor();

    }

    @Override
    public void run(ImageProcessor ip) {

       GenericDialog gd = new GenericDialog("Thresholding");
        gd.addNumericField("Threshold value:", 128, 0);
        gd.addCheckbox("Correct uneven illumination", false);
        gd.showDialog();

        //check if the dialog was canceled
        if (gd.wasCanceled())
            return;

        //get user choices
        int threshold = (int) gd.getNextNumber();
        boolean correct = gd.getNextBoolean();

        //correct illumination if selected
        ImageProcessor ipCopy;
        if (correct) {
            ipCopy = correctIllumination(ip);

        } else {
            ipCopy = ip;
        }
        // threshold the image
        ByteProcessor thresholdedIp = threshold(ipCopy, threshold);
        ImagePlus thresholdedImage = new ImagePlus("Thresholded Image", thresholdedIp);
        thresholdedImage.show();
    }


    public static void main(String[] args) {
        ImagePlus imp = IJ.openImage();

        Task_1_Threshold task1Threshold = new Task_1_Threshold();
        int setupResult = task1Threshold.setup("", imp);

        if ((setupResult & PlugInFilter.DOES_8G) == 0) {
            IJ.showMessage("Error", "This plugin only works with 8-bit grayscale images");
            return;
        }

        ImageProcessor imageProcessor = imp.getProcessor();
        task1Threshold.run(imageProcessor);

    }

}
