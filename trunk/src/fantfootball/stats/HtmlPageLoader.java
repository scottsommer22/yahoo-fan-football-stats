package fantfootball.stats;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Class which requests an HTML document.
 */
public class HtmlPageLoader {
    private String cookie;

    /**
     * Create a page loader with a particular cookie. This will be added to the
     * headers for any requests. This can be used to avoid sign in for some
     * websites (such as Yahoo).
     * 
     * @param cookie
     *            the cookie header to send. Multiple cookies can be combined in
     *            the string
     */
    public HtmlPageLoader(String cookie) {
        this.cookie = cookie;
    }

    /**
     * Get the html for a particular url.
     * 
     * @param url
     *            the url to retrieve
     * @return a string representation of the html
     */
    public String getHtml(String url) {

        DefaultHttpClient httpclient = new DefaultHttpClient();

        try {
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Cookie", cookie);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            StringBuilder builder = new StringBuilder();
            drainStream(entity.getContent(), builder);

            return builder.toString();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    /**
     * Drain the stream into the StringBuilder.
     * 
     * @param stream
     *            the stream to read from
     * @param builder
     *            the builder to put the contents into
     */
    private void drainStream(InputStream stream, StringBuilder builder) {
        try {
            int c;
            while ((c = stream.read()) != -1) {
                builder.append((char) c);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
