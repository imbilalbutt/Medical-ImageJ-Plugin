import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
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

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        this.img = imagePlus;
        this.s = s;
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {

    }
}
