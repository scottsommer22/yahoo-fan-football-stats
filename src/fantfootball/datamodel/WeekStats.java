package fantfootball.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WeekStats {

    private Map<Team, TeamStats> teams = new HashMap<Team, TeamStats>();
    private int week;
    
    public WeekStats(int week){
        this.week = week;
    }
    
    public List<TeamStats> getAllStats(){
        return Collections.unmodifiableList(new ArrayList<TeamStats>(teams.values()));
    }
    
    public Set<Team> getAllTeams(){
        return teams.keySet();
    }
    
    public TeamStats getTeam(Team team){
        return teams.get(team);
    }


    public int getWeek() {
        return week;
    }

    public void addPlayerStat(PlayerStat playerStat) {
      
        TeamStats teamStats = teams.get(playerStat.getTeam());
        
        if(teamStats == null){
            teamStats = new TeamStats(playerStat.getTeam());
            teams.put(playerStat.getTeam(), teamStats);
        }
        
        teamStats.addPlayerStat(playerStat);
        
    }
    
}
