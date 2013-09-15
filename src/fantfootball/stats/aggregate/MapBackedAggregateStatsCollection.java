package fantfootball.stats.aggregate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fantfootball.datamodel.Team;
import fantfootball.stats.aggregate.AggregateStat.AggregateStatKey;

@SuppressWarnings("unchecked")
/**
 * Not elegant but works for now
 */
public class MapBackedAggregateStatsCollection implements AggregateStatCollection {

    private Map<Object,Object> stats = new HashMap<Object,Object>();
    
    @Override
    public <T> void addStat(AggregateStat<T> stat) {
        
        List<AggregateStat<T>> statList = (List<AggregateStat<T>>) stats.get(stat.getKey());
        
        if(statList == null){
            statList = new ArrayList<AggregateStat<T>>();
            stats.put(stat.getKey(), statList);
        }
        
        statList.add(stat);
    }

    @Override

    public <T> AggregateStat<T> getStat(AggregateStatKey key, Team team) {
        
        List<AggregateStat<T>> statList = (List<AggregateStat<T>>) stats.get(key);
        
        if(statList == null){
            return null;
        }
        
        for(AggregateStat<T> stat : statList){
            if(stat.getTeam().equals(team)){
                return stat;
            }
        }
        
        return null;
        
    }

    @Override
    public List<AggregateStat<?>> getAll() {

        List<AggregateStat<?>> allStats = new ArrayList<AggregateStat<?>>();
        
        for(Object statObj : stats.values()){
            List<AggregateStat<?>> statList = (List<AggregateStat<?>>) statObj;
            allStats.addAll(statList);
        }
        
        return allStats;
    }

    @Override
    public <T> List<AggregateStat<T>> getStats(AggregateStatKey key) {
        return (List<AggregateStat<T>>) stats.get(key);
    }

}
