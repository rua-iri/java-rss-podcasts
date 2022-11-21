import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class PodPlayer extends JFrame {


    PodPlayer() {
        // set up default functioning for window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        this.setPreferredSize(new Dimension(500, 500));
    }

}
