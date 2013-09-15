package fantfootball.stats.aggregate;

import java.util.List;

import fantfootball.datamodel.Team;
import fantfootball.stats.aggregate.AggregateStat.AggregateStatKey;

public interface AggregateStatCollection {

    <T> void addStat(AggregateStat<T> stat);
    
    <T> AggregateStat<T> getStat(AggregateStatKey key, Team team);
    
    <T> List<AggregateStat<T>> getStats(AggregateStatKey key);
    
    List<AggregateStat<?>> getAll();
}
