import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class PodcastFeed {

    // instance variables for the podcast's url and its feed's content
    private String feedUrl;
    private String feedContent;

    // constructor method
    PodcastFeed(String feedUrl) {

        this.feedUrl = feedUrl;
        this.feedContent = feedContent;

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

    public String toString() {
        return this.feedUrl + "\n" + this.feedContent;
    }

}
