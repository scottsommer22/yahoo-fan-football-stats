package fantfootball.stats;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fantfootball.datamodel.Player;
import fantfootball.datamodel.PlayerStat;
import fantfootball.datamodel.Position;
import fantfootball.datamodel.Team;



/**
 * A object that extracts the player stats from the yahoo html.
 */
public class YahooPlayerStatsDownloader {


    private static final String YAHOO = "http://football.fantasysports.yahoo.com";

    private static final Logger LOGGER = Logger.getLogger(YahooPlayerStatsDownloader.class);

    private static final String TEAM_TAG = "yfa-td-flex Grid-h-mid Px-sm";
    
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

            TagNode[] teams = root.getElementsByAttValue("class", TEAM_TAG, true, true);
            LOGGER.debug("Found " + teams.length + " teams");

            List<Team> allTeams = new ArrayList<Team>();
            for (TagNode team : teams) {
                if (team.getName().equals("td")) {
                    TagNode[] node = team.getElementsByName("a", true);
                    String name = node[0].getText().toString();
                    String link = node[0].getAttributeByName("href");              
                    String key = getKeyFromUrl(link);
                    
                    Team t = new Team(name, link,Integer.parseInt(key));
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
            Document doc = new DomSerializer(new CleanerProperties()).createDOM(root);

            List<PlayerStat> stats = new ArrayList<PlayerStat>();
            getPlayers(doc, stats, week, team);
            getPositions(doc, stats);
            getPerformance(doc, stats);

            return stats;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    
    private void getPerformance(Document doc, List<PlayerStat> stats) {
        List<Node> players = getNodesFromClass(doc, "pps has-stat-note","a");
        for (int i = 0; i < stats.size(); i++) {
            Node player = players.get(i);
            String points = player.getTextContent();
            String projected = player.getParentNode().getNextSibling().getTextContent();
            String percentage = player.getParentNode().getNextSibling().getNextSibling().getTextContent();
            
            double converted = Double.parseDouble(percentage.replace("%", ""))/100;
            
            PlayerStat stat = stats.get(i);
            stat.setPoints(Double.parseDouble(points));
            stat.setProjectedPoints(Double.parseDouble(projected));
            stat.setPerctStart(converted);
           // LOGGER.debug(stat);
        }
    }


    private void getPositions(Document doc, List<PlayerStat> stats) {
        List<String> positions = getNodeTextFromClass(doc, "pos-label","span");
        for (int i = 0; i < stats.size(); i++) {
            stats.get(i).setPosition(Position.fromTag(positions.get(i)));
            //LOGGER.debug(stats.get(i));
        }
    }

    private void getPlayers(Document doc, List<PlayerStat> stats, int week, Team team) {
        //List<String> players = getNodeText(root, PLAYER_NAME_TAG,"a");
        List<Player> players = getPlayers(doc);
        for (Player player : players) {
            PlayerStat stat = new PlayerStat(week, team);
            stat.setPlayer(player);
            stats.add(stat);
           // LOGGER.debug(stat);
        }
    }
    
    private List<Player> getPlayers(Document doc){
       
        try{
        
            List<Player> players = new ArrayList<Player>();
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            String path = "//div[contains(@class,'ysf-player-name')]";
            NodeList nl = (NodeList) xpath.evaluate(path, doc, XPathConstants.NODESET);
            
            //Go through each player
            for(int i = 0; i < nl.getLength(); i++){
                Node n = nl.item(i);
                Node name = n.getFirstChild();
                Node posTeam = name.getNextSibling().getNextSibling();
                
                Player p = new Player();
                p.setName(name.getTextContent());
                String playerURL = name.getAttributes().getNamedItem("href").getTextContent();
                String playerKey = getKeyFromUrl(playerURL);
                p.setKey(playerKey);
                
                String positionTeam = posTeam.getTextContent();
                int split = positionTeam.indexOf("-");
                p.setTeam(positionTeam.substring(0, split-1));
                String positionTag = positionTeam.substring(split+2,positionTeam.length());
                p.setPosition(Position.fromTag(positionTag));
                
                players.add(p);
            }
                        
            return players;
        } catch(Exception e){
            throw new IllegalStateException(e);
        }
    }

    private String getKeyFromUrl(String url){
        
        String[] tokens = url.split("/");
        
        String key = tokens[tokens.length-1];
        
        return key;
    }
    

    private List<String> getNodeTextFromClass(Document doc, String className, String nodeType) {
        try{
        List<String> values = new ArrayList<String>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String path = "//"+ nodeType + "[contains(@class,'"+className+ "')]/text()";
        NodeList nl = (NodeList) xpath.evaluate(path, doc, XPathConstants.NODESET);

        if(nl == null || nl.getLength() < 1){
            LOGGER.warn("FOUND ZERO NODES FOR " + className);
            return values;
        }

        for (int i = 0; i<nl.getLength(); i++){
            Node n = nl.item(i);
            String value = n.getTextContent();
            values.add(value);
        }
        
        return values;
    
        }catch(Exception e){
            throw new IllegalStateException(e);
        }

    }

    private List<Node> getNodesFromClass(Document doc, String className, String nodeType) {
        try{
        List<Node> values = new ArrayList<Node>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String path = "//"+ nodeType + "[contains(@class,'"+className+ "')]";
        NodeList nl = (NodeList) xpath.evaluate(path, doc, XPathConstants.NODESET);

        if(nl == null || nl.getLength() < 1){
            LOGGER.warn("FOUND ZERO NODES FOR " + className);
            return values;
        }

        for (int i = 0; i<nl.getLength(); i++){
            Node n = nl.item(i);
            values.add(n);
        }
        
        return values;
    
        }catch(Exception e){
            throw new IllegalStateException(e);
        }

    }
    
}
    