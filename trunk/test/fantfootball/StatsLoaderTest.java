package fantfootball;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import fantfootball.stats.HtmlPageLoader;
import fantfootball.stats.PlayerStat;
import fantfootball.stats.YahooPlayerStatsDownloader;

public class StatsLoaderTest {

    private static final Logger LOGGER = Logger.getLogger(StatsLoaderTest.class);

    private static final String COOKIE = "B=4doddb192pmud&b=4&d=T8KSoKNpYELlSK7ezysIzIbosQv611lyj45jtg--&s=ag&i=kCIIlQB5uDoFLMtqQgNK; __qca=P0-653712667-1378671567828; __atuvc=2%7C37; ywadp1000800974962=3560227731; fpc1000800974962=ZY7n1gxX|AKCWkojNaa|fses1000800974962=|AKCWkojNaa|ZY7n1gxX|fvis1000800974962=|8MHo0H8107|8MHo0H8107|8MHo0H8107|8|8MHo0H8107|8MHo0H8107; ucs=bnas=0; ypcdb=799e3068ec85746b01166c3d1575e66d; AO=o=0; YLS=v=1&p=1&n=0; F=a=7N6wsLQMvSqC7wQUTdHV40p5OC1GEb7y_vOwnFDQoKPQ6PwCz6WEgGSfJvHouoimUfFUBxk-&b=43yM; Y=v=1&n=bli2ghggn3q33&l=0bj140ijr/o&p=m2h06nq413000100&jb=26|34|10&r=bh&lg=en-US&intl=us; SPT=d=_d8Sw9ZOR69Gyg_wpg1bdeiYl8waLXal2g5Ga7.W_hq2aMxtbeAvH5zHUXuK22k2egxelfUIvsJihef.GEHGqd4AV2zq_rry_XjKMEqF9qBIL1faEQ--&v=1; SPTB=d=Eua0m3gkha5vGbCbJpritoPjrTq9blJqwcX7lr7h.uCSlCrk1dnh9vEfG5w4jnsmyzoK7wU_naGwAFiPDKwupQmJShiESau4s4zqcA--&v=1; HP=0; PH=fn=ufGjdPcscIp9nToAETQ-&l=en-US&i=us; T=z=bpRLSBb94PSBkeUkXLyko.kNjM1NAY1NjVOMzc2Mzc0&a=QAE&sk=DAAO8sPtEzBlLL&ks=EAA669BA3t6TwZnbETRb9Kg1g--~E&d=c2wBTVRReU13RXlNVEk1TkRBeE5EQXoBYQFRQUUBZwFMSVBRN0xVUENXREtSQkdKUzVCWVNXU01QSQFzY2lkAUY3cnFIV1ZOUTUudTIzZGs5YU9FUVpLZFhjMC0BYWMBQU1Tb1pYUjMBdGlwAUlWNEw5QgFzYwF3bAF6egFicFJMU0JBN0U-; U=mt=uKsc352MhYi1SsdRsUajFV1QolKRUstp0Dos7kM-&ux=kpRLSB&un=bli2ghggn3q33";

    private static final String LEAGUE_KEY = "732944";

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
