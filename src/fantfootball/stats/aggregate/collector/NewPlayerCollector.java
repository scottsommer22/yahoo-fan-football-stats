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

/**
 * Collect information on new starters and new to team
 */
public class NewPlayerCollector implements StatCollector {

    @Override
    public void collect(AggregateStatCollection aggStats, LeagueStats leagueStats) {
        
        // Skip first week
        int i = 2;
        WeekStats currWeekStats = leagueStats.getWeek(i);
      
        while(currWeekStats != null){
            WeekStats prevWeekStats = leagueStats.getWeek(i-1);
            
            // Lets assume the teams don't change
            Set<Team> teams = currWeekStats.getAllTeams();
            
            for(Team team : teams){
                              
                StatsCollection currWeek = currWeekStats.getTeam(team).getStats();
                StatsCollection prevWeek = prevWeekStats.getTeam(team).getStats();
                
                for(PlayerStat currWeekStat : currWeek.getStatsAsList()){
                    
                    List<PlayerStat> prevWeekStat = prevWeek.getAllForPlayer(currWeekStat.getPlayer());
                    
                    //New if empty
                    if(prevWeekStat.size() == 0){
                        currWeekStat.setNewToTeam(true);
                       
                        if(!currWeekStat.getPosition().equals(Position.BN)){
                            currWeekStat.setNewInStartingLineUp(true);
                        }
                    }else{
                    
                        //If on the team last week, check if started
                        Position lastWeekPosition = prevWeekStat.get(0).getPosition();
                        if(lastWeekPosition.equals(Position.BN) && !currWeekStat.getPosition().equals(Position.BN)){
                            currWeekStat.setNewInStartingLineUp(true);
                        }
                    }
                    
                }
                

            }
        
            i++;
            currWeekStats = leagueStats.getWeek(i);
            
        }
        
    }

}
