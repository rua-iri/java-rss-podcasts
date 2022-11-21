import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;

public class AppFrame extends JFrame implements ActionListener {

    JButton button;
    JTextField textField;
    JLabel label;
    PodcastFeed pFeed;
    

    AppFrame() {

        //add an app icon and title to the window
        ImageIcon appIcon = new ImageIcon("rss-reader/src/podcast-icon.png");
        this.setIconImage(appIcon.getImage());
        this.setTitle("Rory's RSS Podcast Player");

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

        // if button is pressed
        if (e.getSource() == button) {

            // Store box input text
            String boxText = textField.getText();

            try {

                // make a request to the server
                HttpClient podClient = HttpClient.newHttpClient();
                HttpRequest podRequest = HttpRequest.newBuilder(
                        URI.create(boxText))
                        .build();

                HttpResponse<String> podResponse = podClient.send(podRequest, BodyHandlers.ofString());

                // store feed content to string and instantiate a new podcast
                String podFeed = podResponse.body();
                this.pFeed = new PodcastFeed(boxText, podFeed);

                loadEpis();

            } catch (IOException err) {
                err.printStackTrace();
            } catch (InterruptedException err) {
                err.printStackTrace();
            } catch (IllegalArgumentException err) {

                // TODO create error popup for this

                System.out.println("\n\nError: Invalid URL\n\n");

            }

        }

    }

    //Load episodes into the frame
    public void loadEpis() {

        // reset frame and add information from the
        this.revalidate();
        this.repaint();

        this.remove(label);
        this.remove(button);
        this.remove(textField);

        this.setLayout(new GridLayout(20, 2, 10, 20));


        this.add(new JLabel(pFeed.getPodName()));
        this.add(new JLabel());

        JPanel epPanel = new JPanel();

        // iterate through each episode and display it as a new JLabel
        for (int i = 0; i < 10; i++) {
            this.add(new JLabel(this.pFeed.getPodEpisodes()[i]));
            this.add(new JLabel(this.pFeed.getPodLinks()[i]));
        }
        this.pack();

    }

}
