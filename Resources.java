import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;
class Resources {
    public static URL get(String path) {
        return Resources.class.getResource(path);
    }
    public static ImageIcon getAsImageIcon(String path) {
        return new ImageIcon(get(path));
    }
    public static Image getAsImage(String path) {
        return getAsImageIcon(path).getImage();
    }
}