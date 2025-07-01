import ij.process.ImageProcessor;

public class EvaluationResult {

    double specificity;
    double sensitivity;

    public EvaluationResult(double specificity, double sensitivity) {
        this.specificity = specificity;
        this.sensitivity = sensitivity;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public double getSpecificity() {
        return specificity;
    }

    public EvaluationResult evaluateSegmentation (ImageProcessor segmentation , ImageProcessor reference ){
        EvaluationResult evaluationResult = new EvaluationResult(0,0);

        if(segmentation.getWidth() != reference.getWidth() || segmentation.getHeight() != reference.getHeight()){
            throw new IllegalArgumentException("Segmentation and reference image must have the same dimensions");
        }
    //        Sensitivity = TP/(TP+FN)
    //          Specificity = TN/(TN+FP)


        int tp = 0; // True Positives
        int tn = 0; // True Negatives
        int fp = 0; // False Positives
        int fn = 0; // False Negatives

        int count = 0;
        for (int x = 0; x < segmentation.getWidth(); x++) {
            for (int y = 0; y < segmentation.getHeight(); y++) {
//                if (segmentation.getPixel(x, y) == reference.getPixel(x, y)) {
//                    count++;
//                }

                int segPixel = segmentation.getPixel(x, y);
                int refPixel = reference.getPixel(x, y);

                // Assuming white (255) is target and black (0) is background
                boolean isSegmented = segPixel > 128; // Threshold for considering as target
                boolean isReference = refPixel > 128;

                if (isReference && isSegmented) {
                    tp++;
                } else if (!isReference && !isSegmented) {
                    tn++;
                } else if (!isReference && isSegmented) {
                    fp++;
                } else if (isReference && !isSegmented) {
                    fn++;
                }
            }
        }

        double sensitivity = (tp + fn) == 0 ? 0 : (double) tp / (tp + fn);
        double specificity = (tn + fp) == 0 ? 0 : (double) tn / (tn + fp);

        return new EvaluationResult(specificity, sensitivity);

    }

    public void setSpecificity(double specificity) {
        this.specificity = specificity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getPrecision() {
        return specificity;
    }

    public double getRecall() {
        return sensitivity;
    }

    public double getAccuracy() {
        return (specificity + sensitivity) / 2;
    }

    public double getF1() {
        return 2 * (specificity * sensitivity) / (specificity + sensitivity);
    }

    @Override
    public String toString() {
        return "Specificity: " + specificity + "\nSensitivity: " + sensitivity;
    }
}
