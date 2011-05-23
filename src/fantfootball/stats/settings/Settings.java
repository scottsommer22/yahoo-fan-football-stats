package fantfootball.stats.settings;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Javabean representing the setting for a request to the YahooPlayerStatsSaver.
 */
@XmlRootElement
public class Settings {

    private String league = "";
    private String cookie = "";
    private int fromWeek = 1;
    private int toWeek = 1;
    private String location = "stats.csv";

    public Settings() {
    }

    /**
     * Create a settings object with fiels initialized.
     * 
     * @param league
     *            the league key, found in the yahoo link after clicking league
     *            after the "f1"
     * @param cookie
     *            the cookie need to log into yahoo. Best found by signing into
     *            yahoo fantasy football via a browser and viewing the request
     *            headers through a tool such as FireBug.
     * @param fromWeek
     *            week number to start with (inclusive)
     * @param toWeek
     *            week number to end with (inclusive)
     * @param location
     *            the location to save the data to
     */
    public Settings(String league, String cookie, int fromWeek, int toWeek, String location) {
        super();
        this.league = league;
        this.cookie = cookie;
        this.fromWeek = fromWeek;
        this.toWeek = toWeek;
        this.location = location;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getFromWeek() {
        return fromWeek;
    }

    public void setFromWeek(int fromWeek) {
        this.fromWeek = fromWeek;
    }

    public int getToWeek() {
        return toWeek;
    }

    public void setToWeek(int toWeek) {
        this.toWeek = toWeek;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
