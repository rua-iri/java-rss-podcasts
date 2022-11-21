
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class PodcastFeed {

    // instance variables for the podcast's url and its feed's content
    private String feedUrl;
    private String feedContent;
    private int numEpisodes;
    private String podName;
    private String[] podEpisodes;
    private String[] podLinks;

    // constructor method
    PodcastFeed(String feedUrl, String feedContent) {

        this.feedUrl = feedUrl;
        this.feedContent = feedContent;

        try {
            //parse the rss feed
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inSo = new InputSource(new StringReader(this.feedContent));
            Document doc = builder.parse(inSo);
            doc.getDocumentElement().normalize();

            //set the number of episodes and the podcast's name
            this.numEpisodes = doc.getElementsByTagName("item").getLength();
            this.podName = doc.getElementsByTagName("title").item(0).getTextContent();

            //declare the size of these arrays
            this.podEpisodes = new String[this.numEpisodes];
            this.podLinks = new String[this.numEpisodes];


            //iterate through every podcast episode
            for (int i = 0; i < numEpisodes; i++) {

                //store each value in the arrays
                this.podEpisodes[i] = doc.getElementsByTagName("title").item(i + 2).getTextContent();
                this.podLinks[i] = doc.getElementsByTagName("enclosure").item(i).getAttributes().item(2).getNodeValue();

                

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // setter and getter methods
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getFeedUrl() {
        return this.feedUrl;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public String getFeedContent() {
        return this.feedContent;
    }

    public int getNumEpisodes() {
        return numEpisodes;
    }

    public void setNumEpisodes(int numEpisodes) {
        this.numEpisodes = numEpisodes;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public String[] getPodEpisodes() {
        return podEpisodes;
    }

    public void setPodEpisodes(String[] podEpisodes) {
        this.podEpisodes = podEpisodes;
    }

    public String[] getPodLinks() {
        return podLinks;
    }

    public void setPodLinks(String[] podLinks) {
        this.podLinks = podLinks;
    }

    public String toString() {
        return this.feedUrl + "\n" + this.feedContent;
    }

}
