package fantfootball.stats;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

/**
 * A object that extracts the player stats from the yahoo html.
 */
public class YahooPlayerStatsDownloader {

    /**
     * Class representing the details of a team.
     */
    private static class Team {

        private String name;

        private String link;

        public Team(String name, String link) {
            this.name = name;
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public String toString() {
            return name + "," + link;
        }

    }

    private static final String YAHOO = "http://football.fantasysports.yahoo.com";

    private static final Logger LOGGER = Logger.getLogger(YahooPlayerStatsDownloader.class);

    private HtmlPageLoader loader;

    private String leagueKey;

    /**
     * Create the a downloader for the specified league key and cookie. See
     * YahooPageLoader for details on the cookie.
     * 
     * @param loader
     *            The loader to request the html pages with
     * @param leagueKey
     *            the number for the league
     */
    public YahooPlayerStatsDownloader(HtmlPageLoader loader, String leagueKey) {
        super();
        this.loader = loader;
        this.leagueKey = leagueKey;
    }

    /**
     * Get the stats for all teams in the league for the range specified.
     * 
     * @param startWeek
     *            start week (inclusive)
     * @param endWeek
     *            end week (inclusive)
     * @return a list of the stats
     */
    public List<PlayerStat> getLeaugeStats(int startWeek, int endWeek) {
        List<Team> teams = getTeams();

        List<PlayerStat> stats = new ArrayList<PlayerStat>();
        for (Team team : teams) {
            for (int w = startWeek; w <= endWeek; w++) {
                LOGGER.debug("Loading " + team);
                List<PlayerStat> weekStats = getWeekStats(w, team);
                stats.addAll(weekStats);

                for (PlayerStat stat : weekStats) {
                    LOGGER.debug(stat);
                }

            }
        }

        return stats;

    }

    /**
     * Load all the teams in a league.
     * 
     * @return a list of teams
     */
    private List<Team> getTeams() {
        try {

            String url = YAHOO + "/f1/" + leagueKey + "/?lhst=stand";
            LOGGER.debug("loading " + url);
            String html = loader.getHtml(url);

            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode root = cleaner.clean(html);

            TagNode[] teams = root.getElementsByAttValue("class", "team", true, true);
            LOGGER.debug("Found " + teams.length + " teams");

            List<Team> allTeams = new ArrayList<Team>();
            for (TagNode team : teams) {
                if (team.getName().equals("td")) {
                    TagNode[] node = team.getElementsByName("a", true);
                    String name = node[0].getText().toString();
                    String link = node[0].getAttributeByName("href");
                    Team t = new Team(name, link);
                    allTeams.add(t);
                    LOGGER.debug(t);

                }
            }

            return allTeams;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get the stats for one team for one week.
     * 
     * @param week
     *            the week number
     * @param team
     *            the team
     * @return a List of stats
     */
    private List<PlayerStat> getWeekStats(int week, Team team) {

        try {
            String html = loader.getHtml(YAHOO + team.getLink() + "?week=" + week);
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode root = cleaner.clean(html);

            List<PlayerStat> stats = new ArrayList<PlayerStat>();
            getPlayers(root, stats, week, team.getName());
            getPositions(root, stats);
            getPoints(root, stats);

            // LOGGER.debug(html);
            return stats;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Extract the points scored for each player.
     * 
     * @param root
     *            the Html node
     * @param stats
     *            the player stats to update
     */
    private void getPoints(TagNode root, List<PlayerStat> stats) {
        List<String> points = getFieldByClassFromTd(root, "pts last");
        for (int i = 0; i < stats.size(); i++) {
            stats.get(i).setPoints(points.get(i));
        }
    }

    /**
     * Extract each players position.
     * 
     * @param root
     *            the Html node to search
     * @param stats
     *            the stats to update
     */
    private void getPositions(TagNode root, List<PlayerStat> stats) {
        List<String> positions = getFieldByClassFromTd(root, "pos first");
        for (int i = 0; i < stats.size(); i++) {
            stats.get(i).setPosition(positions.get(i));
        }
    }

    /**
     * Get all the players on a team.
     * 
     * @param root
     *            the Html node to search
     * @param stats
     *            the stats to initialize
     * @param week
     *            the week this is for
     * @param name
     *            the name of the team manager
     */
    private void getPlayers(TagNode root, List<PlayerStat> stats, int week, String name) {
        List<String> players = getFieldByClassFromTd(root, "player");
        for (String player : players) {
            PlayerStat stat = new PlayerStat(week, name);
            int end = player.indexOf(")");
            stat.setPlayer(player.substring(0, end + 1));
            stats.add(stat);
        }
    }

    /**
     * Extract a field from a Html table node.
     * 
     * @param root
     *            the Html node to search
     * @param className
     *            the class name to extract
     * @return an array of all the values in the order found
     */
    private List<String> getFieldByClassFromTd(TagNode root, String className) {
        List<String> values = new ArrayList<String>();
        TagNode[] positionNodes = root.getElementsByAttValue("class", className, true, true);

        for (TagNode node : positionNodes) {
            if (node.getName().equals("td")) {
                values.add(node.getText().toString());
            }
        }

        return values;
    }

}