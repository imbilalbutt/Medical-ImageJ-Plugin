import ij.IJ;
import ij.plugin.PlugIn;

public class Setup_Test implements PlugIn {
    @Override
    public void run(String s) {
        IJ.showMessage("If you can read this, the Project-Setup most likely worked. \n " +
                            "                                 Happy Coding :)               ");
    }

    public static void main(String[] args) {
        Setup_Test plugin = new Setup_Test();
        plugin.run("");
    }
}
