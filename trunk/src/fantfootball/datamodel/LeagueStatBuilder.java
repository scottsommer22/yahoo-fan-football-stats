package fantfootball.datamodel;

import java.util.List;


public class LeagueStatBuilder {


    public static LeagueStats categorize(List<PlayerStat> stats) {
        // Create week and team stats
        LeagueStats leagueStats = new LeagueStats();
        
        for(PlayerStat stat : stats){
            leagueStats.addPlayerStat(stat);
        }
        
        return leagueStats;
    }
    
    
    
}
