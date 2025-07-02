import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class Task_5_CannyEdgeDetection implements PlugInFilter {


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
        Task_5_CannyEdgeDetection plugin = new Task_5_CannyEdgeDetection();
        int setupResult = plugin.setup("", imp);

        // Verify the image type is supported (8-bit grayscale)
        if ((setupResult & PlugInFilter.DOES_8G) == 0) {
            IJ.showMessage("Error", "This plugin requires an 8-bit grayscale image");
            return;
        }

        // Run the plugin on the image
        plugin.run(imp.getProcessor());
    }

    private ImagePlus imp;

    @Override
    public void run(ImageProcessor ip) {

        GenericDialog gd = new GenericDialog("Canny Edge Detection Parameters");
        gd.addNumericField("Gaussian blur sigma (σ):", 2.0, 1);
        gd.addNumericField("Upper threshold (% of max):", 15, 0);
        gd.addNumericField("Lower threshold (% of max):", 5, 0);
        gd.showDialog();

        if (gd.wasCanceled()) return;

        double sigma = gd.getNextNumber();
        int upperThresholdPercent = (int)gd.getNextNumber();
        int lowerThresholdPercent = (int)gd.getNextNumber();

        int[][] SobelX = {{1,0,-1},{2,0,-2},{1,0,-1}};
        int[][] SobelY = {{1,2,1},{0,0,0},{-1,-2,-1}};

        FloatProcessor fp = ip.convertToFloatProcessor();
        fp.blurGaussian(sigma);

        Task_4_Filters task4Filters = new Task_4_Filters();
        FloatProcessor gradX = task4Filters.applyFilter(fp, SobelX);
        FloatProcessor gradY = task4Filters.applyFilter(fp, SobelY);
        FloatProcessor gradient = task4Filters.getGradient(gradX, gradY);

        ByteProcessor directions = getDir(gradX, gradY);

        FloatProcessor suppressed = nonMaxSuppress(gradient, directions);

        ByteProcessor edges = hysteresisThreshold(suppressed, upperThresholdPercent, lowerThresholdPercent);

        new ImagePlus("Canny Edges (σ="+sigma+", "+upperThresholdPercent+"/"+lowerThresholdPercent+"%)", edges).show();
    }


    public ByteProcessor getDir (FloatProcessor X_Deriv, FloatProcessor Y_Deriv){
        ByteProcessor dir = new ByteProcessor(X_Deriv.getWidth(), X_Deriv.getHeight());

        int[] angles = {0,45,90,135,180};

        for (int x = 0; x < X_Deriv.getWidth(); x++){
            for (int y = 0; y < X_Deriv.getHeight(); y++) {
                float angle = (float) Math.atan2(-Y_Deriv.getPixel(x,y), X_Deriv.getPixel(x,y));

                // Convert to positive angle (0-180)
                if (angle < 0) angle += 180;

                // Search for the closest match in the angles-array and store the final direction in the output-ByteProcessor.
                int closestAngle = 0;
                int minDiff = Integer.MAX_VALUE;

                for (int a : angles) {
                    int distance = (int) Math.abs(a - angle);
                    if (distance < minDiff) {
                        minDiff = distance;
                        closestAngle = a;
                    }
                }

                if (closestAngle == 180) closestAngle = 0;

                dir.set(x, y, closestAngle);

            }
        }
        return dir;
    }

public FloatProcessor nonMaxSuppress(FloatProcessor grad, ByteProcessor dir) {
    int W = grad.getWidth(), H = grad.getHeight();
    FloatProcessor out = new FloatProcessor(W, H);

    for (int x = 1; x < W - 1; x++) {
        for (int y = 1; y < H - 1; y++) {
            float current = grad.getf(x, y);
            int direction = dir.get(x, y);
            float neighbor1 = 0, neighbor2 = 0;

            switch (direction) {
                case 0:
                    neighbor1 = grad.getf(x - 1, y);
                    neighbor2 = grad.getf(x + 1, y);
                    break;
                case 45:
                    neighbor1 = grad.getf(x - 1, y - 1);
                    neighbor2 = grad.getf(x + 1, y + 1);
                    break;
                case 90:
                    neighbor1 = grad.getf(x, y - 1);
                    neighbor2 = grad.getf(x, y + 1);
                    break;
                case 135:
                    neighbor1 = grad.getf(x + 1, y - 1);
                    neighbor2 = grad.getf(x - 1, y + 1);
                    break;
            }

            out.setf(x, y, (current >= neighbor1 && current >= neighbor2) ? current : 0);
        }
    }
    return out;
}


    public boolean hasNeighbours(ByteProcessor BP, int x, int y ){
        int count = (BP.getPixel(x+1,y)
                + BP.getPixel(x-1,y)
                + BP.getPixel(x,y+1)
                + BP.getPixel(x,y-1)
                + BP.getPixel(x+1,y+1)
                + BP.getPixel(x-1,y-1)
                + BP.getPixel(x-1,y+1)
                + BP.getPixel(x+1,y-1));

        count/=255;
        return (count>0) ;
    }

//    15 as upper and 5 as lower
    public ByteProcessor hysteresisThreshold (FloatProcessor In, int upper, int lower){
        ByteProcessor out = new ByteProcessor(In.getWidth(), In.getHeight());

        float tHigh = ((float)In.getMax()*upper)/100f;
        float tLow = ((float)In.getMax()*lower)/100f;

        for (int x = 0; x < In.getWidth(); x++){
            for (int y = 0; y < In.getHeight(); y++) {
                if (In.getf(x, y) > tHigh) {
                    out.set(x, y, 255);
                }

                if (In.getf(x, y) < tLow) {
                    out.set(x, y, 0);
                }
            }
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int x = 0; x < In.getWidth(); x++) {
                for (int y = 0; y < In.getHeight(); y++) {
                    if (In.getPixelValue(x, y) > tLow && hasNeighbours(out, x, y) && out.getPixel(x,y)==0) {
                        out.set(x, y, 255);
                        changed = true;
                    }
                }
            }
        }

        return out;
    }


    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }
}
