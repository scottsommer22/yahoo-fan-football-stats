package fantfootball.stats.aggregate.collector;

import java.util.List;
import java.util.Set;

import fantfootball.datamodel.LeagueStats;
import fantfootball.datamodel.PlayerStat;
import fantfootball.datamodel.Position;
import fantfootball.datamodel.StatsCollection;
import fantfootball.datamodel.Team;
import fantfootball.datamodel.WeekStats;
import fantfootball.stats.aggregate.AggregateStatCollection;
import fantfootball.stats.aggregate.WeeklyTeamDoubleStat;

public class TeamTotalCollector implements StatCollector {

    @Override
    public void collect(AggregateStatCollection aggStats, LeagueStats leagueStats) {
        
        List<WeekStats> weekStatsList = leagueStats.getAllWeeks();
        
        for(WeekStats weekStats : weekStatsList){
        
            Set<Team> teams = weekStats.getAllTeams();
            
            for(Team team : teams){
                
                WeeklyTeamDoubleStat teamTotal = new WeeklyTeamDoubleStat(AggStatType.TEAM_TOTAL, team, weekStats.getWeek());
                WeeklyTeamDoubleStat teamProj = new WeeklyTeamDoubleStat(AggStatType.TEAM_PROJECTED, team, weekStats.getWeek());
                WeeklyTeamDoubleStat teamUnder = new WeeklyTeamDoubleStat(AggStatType.UNDERPERFORMED, team, weekStats.getWeek());
                WeeklyTeamDoubleStat teamOver = new WeeklyTeamDoubleStat(AggStatType.OVERPERFORMED, team, weekStats.getWeek());
                
                StatsCollection teamStats = weekStats.getTeam(team).getStats();
                
                for(PlayerStat stat : teamStats.getStatsAsList()){
                    if(!stat.getPosition().equals(Position.BN)){
                        teamTotal.add(stat.getPoints());
                        teamProj.add(stat.getProjectedPoints());
                        
                        if(stat.getPoints() > stat.getProjectedPoints()) {
                            teamOver.add(stat.getPoints() - stat.getProjectedPoints());
                        }else{
                            teamUnder.add(stat.getProjectedPoints()-stat.getPoints());
                        }
                            
                        
                    }
                }
                
                aggStats.addStat(teamTotal);
                aggStats.addStat(teamProj);
                aggStats.addStat(teamUnder);
                aggStats.addStat(teamOver);
            }
            
        }
        
    }

}
