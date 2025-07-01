import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Task_5_CannyEdgeDetection implements PlugInFilter {

    @Override
    public void run(ImageProcessor imageProcessor) {
        int[][] SobelX = {{1,0,-1},{2,0,-2},{1,0,-1}};
        int[][] SobelY = {{1,2,1},{0,0,0},{-1,-2,-1}};

    }

    public boolean hasNeighbours(ByteProcessor BP, int x, int y ){
        int count = (BP.getPixel(x+1,y)+BP.getPixel(x-1,y)+BP.getPixel(x,y+1)+BP.getPixel(x,y-1)+BP.getPixel(x+1,y+1)+
                BP.getPixel(x-1,y-1)+BP.getPixel(x-1,y+1)+BP.getPixel(x+1,y-1));
        count/=255;
        return (count>0) ;
    }


    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }
}
