package fantfootball.stats.aggregate.collector;

import java.util.List;
import java.util.Set;

import fantfootball.datamodel.LeagueStats;
import fantfootball.datamodel.PlayerStat;
import fantfootball.datamodel.Position;
import fantfootball.datamodel.StatsCollection;
import fantfootball.datamodel.Team;
import fantfootball.datamodel.WeekStats;
import fantfootball.stats.aggregate.AggregateStat;
import fantfootball.stats.aggregate.AggregateStat.AggregateStatKey;
import fantfootball.stats.aggregate.AggregateStatCollection;

public class PlayerUsageCollector implements StatCollector {

    @Override
    public void collect(AggregateStatCollection aggStats, LeagueStats leagueStats) {
        
        List<WeekStats> weekStatsList = leagueStats.getAllWeeks();
        
        for(WeekStats weekStats : weekStatsList){
        
            Set<Team> teams = weekStats.getAllTeams();
            
            for(Team team : teams){
                               
                StatsCollection teamStats = weekStats.getTeam(team).getStats();
                AggregateStat<Double> teamTotal = aggStats.getStat(new AggregateStatKey(AggStatType.TEAM_TOTAL, weekStats.getWeek()), team);
                
                for(PlayerStat stat : teamStats.getStatsAsList()){
                    if(!stat.getPosition().equals(Position.BN)){
                       stat.setPlayed(true);
                       stat.setPercentTotal(stat.getPoints()/teamTotal.getValue());
                    }
                }

            }
            
        }
        
    }

}
