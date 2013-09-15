package fantfootball.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fantfootball.datamodel.LeagueStatBuilder;
import fantfootball.datamodel.LeagueStats;
import fantfootball.datamodel.PlayerStat;
import fantfootball.datamodel.Position;
import fantfootball.stats.aggregate.AggregateStatCollection;
import fantfootball.stats.aggregate.MapBackedAggregateStatsCollection;
import fantfootball.stats.aggregate.collector.NewPlayerCollector;
import fantfootball.stats.aggregate.collector.OptimalLineupCalculator;
import fantfootball.stats.aggregate.collector.PlayerUsageCollector;
import fantfootball.stats.aggregate.collector.TeamTotalCollector;

public class StatAggregationWorkflow {

	private AggregateStatCollection aggStats = new MapBackedAggregateStatsCollection();

    private static Map<Position, Integer> ROSTER_SPOTS;
    
    
    static{
        ROSTER_SPOTS = new HashMap<Position, Integer>();
        ROSTER_SPOTS.put(Position.QB, 1);
        ROSTER_SPOTS.put(Position.RB, 2);
        ROSTER_SPOTS.put(Position.WR, 2);
        ROSTER_SPOTS.put(Position.TE, 1);
        ROSTER_SPOTS.put(Position.WRT, 2);
        ROSTER_SPOTS.put(Position.K, 1);
        ROSTER_SPOTS.put(Position.DEF, 1);
    }
    
	
	public AggregateStatCollection calculate(List<PlayerStat> stats){
		LeagueStats leagueStats = LeagueStatBuilder.categorize(stats);
		
		// We could inject this, but we will hard code for now
		// Same with the roster spots
		
		new TeamTotalCollector().collect(aggStats, leagueStats);
		new PlayerUsageCollector().collect(aggStats, leagueStats);
		new NewPlayerCollector().collect(aggStats, leagueStats);
		new OptimalLineupCalculator(ROSTER_SPOTS).collect(aggStats, leagueStats);
		
		return aggStats;
		
	}
	
}
