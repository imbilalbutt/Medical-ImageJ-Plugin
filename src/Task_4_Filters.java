import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class Task_4_Filters implements PlugInFilter {

    protected int[][] SobelX = {{1,0,-1},{2,0,-2},{1,0,-1}};
    protected int[][] SobelY = {{1,2,1},{0,0,0},{-1,-2,-1}};

    protected int[][] ScharrX = {{47,0,-47},{162,0,-162},{47,0,-47}};
    protected int[][] ScharrY = {{47,162,47},{0,0,0},{-47,-162,-47}};

    protected int[][] PrewittX = {{1,0,-1},{1,0,-1},{1,0,-1}};
    protected int[][] PrewittY = {{1,1,1},{0,0,0},{-1,-1,-1}};

    protected ImagePlus  img;
    protected String s;


    public static void main(String[] args) {
        // Launch ImageJ if not already running
        new ij.ImageJ();

        // Open an image through the file dialog
        ImagePlus imp = IJ.openImage();

        // Check if an image was actually opened
        if (imp == null) {
            IJ.showMessage("Error", "No image was opened");
            return;
        }

        // Create and configure the plugin
        Task_4_Filters plugin = new Task_4_Filters();
        int setupResult = plugin.setup("", imp);

        // Verify the image type is supported (8-bit grayscale)
        if ((setupResult & PlugInFilter.DOES_8G) == 0) {
            IJ.showMessage("Error", "This plugin requires an 8-bit grayscale image");
            return;
        }

        // Run the plugin on the image
        plugin.run(imp.getProcessor());
    }

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        this.img = imagePlus;
        this.s = s;
        return DOES_8G;
    }


    @Override
    public void run(ImageProcessor ip) {
        // Create dialog for filter selection
        GenericDialog gd = new GenericDialog("Edge Detection Filter");
        String[] filters = {"Sobel", "Scharr", "Prewitt"};
        gd.addChoice("Filter type:", filters, filters[0]);
        gd.showDialog();

        if (gd.wasCanceled()) return;

        // Convert input to FloatProcessor
        FloatProcessor fp = ip.convertToFloatProcessor();

        // Apply selected filter
        int filterIndex = gd.getNextChoiceIndex();
        FloatProcessor gradX, gradY, gradient;

        switch (filterIndex) {
            case 0: // Sobel
                gradX = applyFilter(fp, SobelX);
                gradY = applyFilter(fp, SobelY);
                break;
            case 1: // Scharr
                gradX = applyFilter(fp, ScharrX);
                gradY = applyFilter(fp, ScharrY);
                break;
            case 2: // Prewitt
                gradX = applyFilter(fp, PrewittX);
                gradY = applyFilter(fp, PrewittY);
                break;
            default:
                return;
        }

        gradient = getGradient(gradX, gradY);


        gradient.resetMinAndMax();
        new ImagePlus("Edge Detection (" + filters[filterIndex] + ")", gradient).show();
    }

    public FloatProcessor applyFilter(FloatProcessor In, int[][] kernel) {

        FloatProcessor result = new FloatProcessor(In.getWidth(), In.getHeight());

        int kernelWidth = kernel[0].length;
        int kernelHeight = kernel.length;
        int kernelCenterX = kernelWidth / 2;
        int kernelCenterY = kernelHeight / 2;

        for (int x = kernelCenterX; x < In.getWidth() - kernelCenterX; x++) {
            for (int y = kernelCenterY; y < In.getHeight() - kernelCenterY; y++) {
                float sum = 0;

                // Apply kernel
                for (int i = 0; i < kernelHeight; i++) {
                    for (int j = 0; j < kernelWidth; j++) {
                        int pixelX = x + j - kernelCenterX;
                        int pixelY = y + i - kernelCenterY;
                        sum += kernel[i][j] * In.getPixelValue(pixelX, pixelY);
                    }
                }

                result.setf(x, y, sum);
            }
        }

        return result;
    }

    public FloatProcessor getGradient(FloatProcessor In_X, FloatProcessor In_Y) {

        if (In_X.getWidth() != In_Y.getWidth() || In_X.getHeight() != In_Y.getHeight()) {
            throw new IllegalArgumentException("Input images must have the same dimensions");
        }

        FloatProcessor imageGradient = new FloatProcessor(In_X.getWidth(), In_X.getHeight());

        for (int x = 0; x < In_X.getWidth(); x++) {
            for (int y = 0; y < In_X.getHeight(); y++) {

                float gx = In_X.getf(x, y);
                float gy = In_Y.getf(x, y);

                // Calculate gradient magnitude: sqrt(gx² + gy²)
                float magnitude = (float)Math.sqrt(gx*gx + gy*gy);

                imageGradient.setf(x, y, magnitude);
            }
        }

        return imageGradient;
    }



}
