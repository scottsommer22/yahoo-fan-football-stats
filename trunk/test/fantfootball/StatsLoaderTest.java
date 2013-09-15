package fantfootball;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import fantfootball.datamodel.PlayerStat;
import fantfootball.stats.HtmlPageLoader;
import fantfootball.stats.YahooPlayerStatsDownloader;

public class StatsLoaderTest {

    private static final Logger LOGGER = Logger.getLogger(StatsLoaderTest.class);

    private static final String COOKIE = "NEED TO SET";

    private static final String LEAGUE_KEY = "NEED TO SET";

    @Test
    public void getHtml() {
        
        HtmlPageLoader pageLoader = new HtmlPageLoader(COOKIE);
        YahooPlayerStatsDownloader loader = new YahooPlayerStatsDownloader(pageLoader, LEAGUE_KEY);
        List<PlayerStat> stats = loader.getLeaugeStats(1, 1);

        Assert.assertNotNull(stats);
        Assert.assertTrue(stats.size() > 0);
        
        for (PlayerStat stat : stats) {
            LOGGER.debug(stat);
        }

    }

}
