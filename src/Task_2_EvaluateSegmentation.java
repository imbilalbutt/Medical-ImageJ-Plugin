import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;


public class Task_2_EvaluateSegmentation implements PlugInFilter {

    private ImagePlus imp;

    @Override
    public int setup(String s, ImagePlus imagePlus)  {
        this.imp = imagePlus;
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor segmentation) {
        // 1. Ask user to select the reference image
        GenericDialog gd = new GenericDialog("Select Reference Image");
        String[] imageTitles = ij.WindowManager.getImageTitles();
//        gd.addChoice("Reference Image:", imageTitles, imageTitles.length > 0 ? imageTitles[0] : null);
        gd.addCheckbox("Open new reference image", false);
        gd.showDialog();

        if (gd.wasCanceled()) return;

        ImagePlus ref = IJ.openImage();


        // 2. Get the reference image
//        String refTitle = gd.getNextChoice();
//        ImagePlus refImp = ij.WindowManager.getImage(refTitle);
//        if (refImp == null) {
//            ij.IJ.showMessage("Error", "Reference image not found!");
//            return;
//        }

        // 3. Evaluate the segmentation
        EvaluationResult result = new EvaluationResult(0,0)
                .evaluateSegmentation(segmentation, ref.getProcessor());

        System.out.println(result);

        // 4. Show results
        GenericDialog resultsDialog = new GenericDialog("Segmentation Evaluation Results");
        resultsDialog.addMessage(result.toString());
        resultsDialog.addMessage(String.format("Accuracy: %.3f", result.getAccuracy()));
        resultsDialog.addMessage(String.format("F1 Score: %.3f", result.getF1()));
        resultsDialog.showDialog();
    }

    public static void main(String[] args) {
        // Test the evaluation
        ImagePlus imp = IJ.openImage();

        Task_2_EvaluateSegmentation task2EvaluateSegmentation = new Task_2_EvaluateSegmentation();
        int setupResult = task2EvaluateSegmentation.setup("", imp);

        if ((setupResult & PlugInFilter.DOES_8G) == 0) {
            IJ.showMessage("Error", "This plugin only works with 8-bit grayscale images");
            return;
        }

        ImageProcessor imageProcessor = imp.getProcessor();
        task2EvaluateSegmentation.run(imageProcessor);

    }
}
