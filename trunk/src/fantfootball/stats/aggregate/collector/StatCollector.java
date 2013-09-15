package fantfootball.stats.aggregate.collector;

import fantfootball.datamodel.LeagueStats;
import fantfootball.stats.aggregate.AggregateStatCollection;

/**
 * interface for objects that collect league stats
 *
 * @param <T> data type of stat
 */
public interface StatCollector{
    void collect(AggregateStatCollection aggStats, LeagueStats leagueStats);
}
