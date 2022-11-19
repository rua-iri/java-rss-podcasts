import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class AppFrame extends JFrame implements ActionListener{


    AppFrame() {

        JTextField textField = new JTextField();



        //set some default styling and behaviour for the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,500);

        this.setVisible(true);

        this.add(textField);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
    }


}
