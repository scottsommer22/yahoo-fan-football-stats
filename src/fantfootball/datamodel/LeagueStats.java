package fantfootball.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeagueStats {

    Map<Integer, WeekStats> weeks = new HashMap<Integer, WeekStats>();
    
    
    public List<WeekStats> getAllWeeks(){
        return Collections.unmodifiableList(new ArrayList<WeekStats>(weeks.values()));
    }
    
    public WeekStats getWeek(int i ){
        return weeks.get(i);
    }

    public void addPlayerStat(PlayerStat playerStat) {
        
        int week = playerStat.getWeek();
        
        WeekStats weekStats = weeks.get(week);
        
        if(weekStats == null){
            weekStats = new WeekStats(playerStat.getWeek());
            weeks.put(week, weekStats);
        }
        
        weekStats.addPlayerStat(playerStat);
        
    }

    
}
