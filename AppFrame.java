import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JLabel;


public class AppFrame extends JFrame implements ActionListener{

    JButton button;
    JTextField textField;
    JLabel label;

    AppFrame() {

        //set up default functioning for window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        //instantiate and set size of text input
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));

        //instantiate and establish functioning of button
        button = new JButton("Submit");
        button.addActionListener(this);

        //instantiate label
        label = new JLabel("RSS URL");


        //add all elements and pack
        this.add(label);
        this.add(textField);
        this.add(button);
        this.pack();

        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==button) {
            System.out.println(textField.getText());
        }


    }


}
