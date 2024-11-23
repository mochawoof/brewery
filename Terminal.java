import java.awt.*;
import org.fife.ui.rtextarea.*;

class Terminal extends RTextArea {
    public Terminal() {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setCurrentLineHighlightColor(Color.BLACK);
        setEditable(false);
    }
}