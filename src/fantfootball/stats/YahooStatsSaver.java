package fantfootball.stats;

import java.util.List;

import fantfootball.datamodel.PlayerStat;
import fantfootball.stats.aggregate.AggregateStatCollection;
import fantfootball.stats.settings.JaxbSettingsLoader;
import fantfootball.stats.settings.Settings;
import fantfootball.stats.settings.SettingsLoader;
import fantfootball.stats.writer.CsvPlayerStatWriter;
import fantfootball.stats.writer.PlayerStatWriter;

/**
 * Object to get stats from Yahoo and save. Currently saves to Csv file, but
 * could have a different writer injected with a couple small changes.
 * 
 * The most confusing thing about this is the requirement for a cookie. This is
 * to avoid needing to sign into Yahoo. The best way to retrieve this is to sign
 * in to yahoo on a browser and view the headers sent in subsequent page
 * requests through a tool such as FireBug.
 */
public class YahooStatsSaver {

    private SettingsLoader settingsLoader = new JaxbSettingsLoader();

    private PlayerStatWriter playerWriter = new CsvPlayerStatWriter("stats.csv");
    private PlayerStatWriter aggWriter = new CsvPlayerStatWriter("aggstats.csv");
    
    private StatAggregationWorkflow statAggWorkflow = new StatAggregationWorkflow();

    /**
     * Save the player stats for the league and duration defined in the
     * settings.
     * 
     * @param settings
     *            details of what to save
     */
    public void saveYahooPlayerStats(Settings settings) {

        settingsLoader.setSettings(settings);

        HtmlPageLoader loader = new HtmlPageLoader(settings.getCookie());
        YahooPlayerStatsDownloader statsDownloader = new YahooPlayerStatsDownloader(loader, settings.getLeague());
        List<PlayerStat> stats = statsDownloader.getLeaugeStats(settings.getFromWeek(), settings.getToWeek());

        AggregateStatCollection aggStats = statAggWorkflow.calculate(stats);
        
        ((CsvPlayerStatWriter) playerWriter).setFileName(settings.getLocation());
        ((CsvPlayerStatWriter) aggWriter).setFileName(settings.getAggLocation());
        playerWriter.write(stats);
        aggWriter.write(aggStats.getAll());
    }

    /**
     * Get the last used settings. This allows a calling application to remember
     * the previously used values.
     * 
     * @return the settings
     */
    public Settings getSettings() {
        return settingsLoader.getSettings();
    }
}
