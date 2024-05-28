import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Service to handle URL ping operations.
 */
public class PingService {
    private String urlString;

    /**
     * Returns the URL string.
     *
     * @return The URL string.
     */
    public String getUrlString() {
        return urlString;
    }

    /**
     * Sets the URL string.
     *
     * @param urlString The URL string to set.
     */
    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    /**
     * Pings the specified URL by making an HTTP request.
     *
     * @return true if the URL is reachable (HTTP 200 OK), false otherwise.
     */
    public boolean ping() {
        HttpURLConnection connection = null;
        try {
            int responseCode;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            responseCode = connection.getResponseCode();

            // If HEAD method is not supported, fallback to GET method.
            if (responseCode == HttpURLConnection.HTTP_BAD_METHOD) {
                connection.setRequestMethod("GET");
                responseCode = connection.getResponseCode();
            }

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            throw new RuntimeException("Failed to ping URL: " + urlString, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    /**
     * Validates if the provided URL string is a well-formed URL.
     *
     * @param url The URL string to validate.
     * @return true if the URL is valid, false otherwise.
     */
    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
