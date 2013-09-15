package fantfootball.stats.aggregate.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fantfootball.datamodel.LeagueStats;
import fantfootball.datamodel.PlayerStat;
import fantfootball.datamodel.Position;
import fantfootball.datamodel.StatsCollection;
import fantfootball.datamodel.Team;
import fantfootball.datamodel.WeekStats;
import fantfootball.stats.aggregate.AggregateStatCollection;
import fantfootball.stats.aggregate.WeeklyTeamDoubleStat;

public class OptimalLineupCalculator implements StatCollector {
    
    private Map<Position, Integer> rosterSpots;

    private static class LineUpCalculator{
        
        private Map<Position, Integer> metaData = new HashMap<Position, Integer>();
        private List<PlayerStat> players = new ArrayList<PlayerStat>();
        
        public LineUpCalculator(List<PlayerStat> players, Map<Position,Integer> spots){
            this.players.addAll(players);
            this.metaData = spots;
        }
        
        public Map<Position,List<PlayerStat>> calculateLineup(){
            sortArray();
            Map<Position,List<PlayerStat>> optimalLineup = new HashMap<Position, List<PlayerStat>>();
            
            //now work down array and fill in positions
            for(PlayerStat ps : players){
                Position position = ps.getPlayer().getPosition();
                
                boolean wasAdded = checkPosition(position, ps, optimalLineup);
                
                if(!wasAdded 
                    && (position.equals(Position.WR) ||position.equals(Position.RB)||position.equals(Position.TE))){
                    checkPosition(Position.WRT, ps, optimalLineup);
                }
            }
            
            return optimalLineup;
        }
        
        private boolean checkPosition(Position p, PlayerStat ps, Map<Position,List<PlayerStat>> optimalLineup){
            Integer slots = metaData.get(p);
            
            if(slots == null){
                slots = new Integer(0);
                metaData.put(p, slots);
            }
            
            List<PlayerStat> positionSpots = optimalLineup.get(p);
            if(positionSpots == null){
                positionSpots = new ArrayList<PlayerStat>();
                optimalLineup.put(p,positionSpots);
            }
         
            if(positionSpots.size() < slots){
                positionSpots.add(ps);
                return true;
            }
            return false;
        }
        
        private void sortArray(){
            Collections.sort(players, new Comparator<PlayerStat>() {
                public int compare(PlayerStat o1, PlayerStat o2) {
                    // TODO Auto-generated method stub
                    if(o1.getPoints() < o2.getPoints()){
                        return 1;
                    }else if (o1.getPoints() > o2.getPoints()){
                        return -1;
                    }else {
                        return 0;
                    }
                }
                
            });
        }
        
    }
    
    @Override
    public void collect(AggregateStatCollection aggStats, LeagueStats leagueStats) {
        
        List<WeekStats> weekStatsList = leagueStats.getAllWeeks();
        
        for(WeekStats weekStats : weekStatsList){
        
            Set<Team> teams = weekStats.getAllTeams();
            
            for(Team team : teams){
                StatsCollection teamStats = weekStats.getTeam(team).getStats();
                LineUpCalculator lineUpCalc = new LineUpCalculator(teamStats.getStatsAsList(), rosterSpots);
                Map<Position, List<PlayerStat>> lineUp = lineUpCalc.calculateLineup();
                
                WeeklyTeamDoubleStat optimalScore = new WeeklyTeamDoubleStat(AggStatType.OPTIMAL_SCORE, team, weekStats.getWeek());
                
                for(List<PlayerStat> positions : lineUp.values()){
                    for(PlayerStat player : positions){
                        player.setOnOptimalLineUp(true);
                        optimalScore.add(player.getPoints());
                    }
                }
                
                aggStats.addStat(optimalScore);
                
            }
            
        }
        
    }

    public void setRosterSpots(Map<Position, Integer> rosterSpots) {
        this.rosterSpots = rosterSpots;
    }

}
