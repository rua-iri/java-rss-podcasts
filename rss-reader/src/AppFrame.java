import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JLabel;

public class AppFrame extends JFrame implements ActionListener {

    JButton button;
    JTextField textField;
    JLabel label;

    AppFrame() {

        // set up default functioning for window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        // instantiate and set size of text input
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));

        // instantiate and establish functioning of button
        button = new JButton("Submit");
        button.addActionListener(this);

        // instantiate label
        label = new JLabel("RSS URL");

        // add all elements and pack
        this.add(label);
        this.add(textField);
        this.add(button);
        this.pack();

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            String boxText = textField.getText();

            System.out.println(boxText);

            PodcastFeed pFeed = new PodcastFeed(boxText);

            System.out.println(pFeed.getFeedContent().substring(0, 100));

            HttpClient podClient = HttpClient.newHttpClient();
            HttpRequest podRequest = HttpRequest.newBuilder(
                    URI.create(boxText))
                    .build();

            try {
                var podResponse = podClient.send(podRequest, BodyHandlers.ofString());

                 String heelo = podResponse.body();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // reset frame and add information from the
            this.revalidate();
            this.repaint();

            this.add(new JLabel("RSS URL"));
            this.pack();

            // this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        }

    }

}
