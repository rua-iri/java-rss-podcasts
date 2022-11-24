import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AppFrame extends JFrame implements ActionListener {

    JButton button;
    JButton nextButton;
    JTextField textField;
    JLabel label;
    PodcastFeed pFeed;

    int episodeStart = 0;
    int episodeLimit;

    AppFrame() {

        // add an app icon and title to the window
        ImageIcon appIcon = new ImageIcon("rss-reader/src/podcast-icon.png");
        this.setIconImage(appIcon.getImage());
        this.setTitle("Rory's RSS Podcast Player");

        // set up default functioning for window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        this.setPreferredSize(new Dimension(450, 150));
        // this.setResizable(false);

        // instantiate and set size of text input
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 40));

        // instantiate label
        label = new JLabel("RSS URL");
        label.setPreferredSize(new Dimension(100, 40));

        // instantiate and establish functioning of button
        button = new JButton("Submit");
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(400, 40));

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

        if (e.getSource() == nextButton) {

            // check that there are enough episodes left
            if (episodeLimit < pFeed.getNumEpisodes()) {
                episodeStart += 10;
            }

            try {

                //open vlc with the link desired
                Process process = Runtime.getRuntime().exec("vlc " + pFeed.getPodLinks()[0]);


            } catch (Exception err) {
                // TODO: handle exception
            }

            loadEpis();
        }

    }

    // Load episodes into the frame
    public void loadEpis() {

        // reset frame and add information from the
        this.revalidate();
        this.repaint();
        this.getContentPane().removeAll();

        this.setPreferredSize(new Dimension(350, 700));

        this.add(new JLabel(pFeed.getPodName()));

        // add the podcast's icon to the frame
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(resizeImage(pFeed.getPodIcon()));
        this.add(logoLabel);

        // check if there are still 10 episodes left to list
        if (episodeStart + 10 <= pFeed.getNumEpisodes()) {
            episodeLimit = episodeStart + 10;
        } else {
            episodeLimit = pFeed.getNumEpisodes();
        }

        // iterate through each episode and display it as a new JLabel
        for (int i = episodeStart; i < episodeLimit; i++) {
            JPanel podcastEpiPanel = new JPanel();
            podcastEpiPanel.add(new JLabel(this.pFeed.getPodEpisodes()[i]));
            podcastEpiPanel.add(new JButton("Listen"));
            podcastEpiPanel.setPreferredSize(new Dimension(350, 35));

            this.add(podcastEpiPanel);
            // this.add(new JLabel(this.pFeed.getPodLinks()[i]));

        }

        // TODO add previous button

        // add button for viewing next set of episodes
        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(250, 40));
        nextButton.addActionListener(this);
        this.add(nextButton);

        this.pack();

    }

    // method for resizing a podcast's icon
    public ImageIcon resizeImage(URL imgLink) {

        ImageIcon logoIcon = new ImageIcon();
        try {
            logoIcon = new ImageIcon(ImageIO.read(imgLink));
            // resize the icon to make it fit
            Image logoImage = logoIcon.getImage();
            logoImage = logoImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(logoImage);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return logoIcon;

    }

}
