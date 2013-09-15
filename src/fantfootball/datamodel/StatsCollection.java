package fantfootball.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatsCollection {
    
    private List<PlayerStat> stats = new ArrayList<PlayerStat>();
    
    private Map<Player, List<PlayerStat>> byPlayer = new HashMap<Player, List<PlayerStat>>();
    
    private Map<Position, List<PlayerStat>> byEligiblePosition = new HashMap<Position, List<PlayerStat>>();
    
    private Map<Position, List<PlayerStat>> byPlayedPosition = new HashMap<Position, List<PlayerStat>>();

    
    public void addPlayerStat(PlayerStat playerStat){
        stats.add(playerStat);
        
        //Inefficient but simple
        createIndexes();
    }
    
    private void createIndexes(){
        
        byPlayer = createMap(stats, new PlayerAttributeGetter <Player>() {
            @Override
            public Player getKey(PlayerStat player) { return player.getPlayer();}
        });
        
        byEligiblePosition = createMap(stats, new PlayerAttributeGetter<Position>() {
            @Override
            public Position getKey(PlayerStat player) { return player.getPlayer().getPosition();}
        });
        
        byPlayedPosition = createMap(stats, new PlayerAttributeGetter <Position>() {
            @Override
            public Position getKey(PlayerStat player) { return player.getPosition();}
        });
        
    }
    
    private <T> Map<T, List<PlayerStat>> createMap(List<PlayerStat> stats, PlayerAttributeGetter<T> getter){
        
        Map<T, List<PlayerStat>> newMap = new HashMap<T, List<PlayerStat>>();
        
        for(PlayerStat stat : stats){
            T key = getter.getKey(stat);
            List<PlayerStat> players = newMap.get(key);
            
            //if first, then create list
            if(players == null){
                players = new ArrayList<PlayerStat>();
                newMap.put(key,players );
            }
            
            players.add(stat);
        }
        return newMap;
    }
    
    public List<PlayerStat> getAllForPlayer(Player player){
        List<PlayerStat> playerStat = byPlayer.get(player);
        
        if(playerStat == null){
            playerStat = new ArrayList<PlayerStat>(0);
        }
        
        return Collections.unmodifiableList(playerStat);
    }
    
    public List<PlayerStat> getStatsAsList(){
        return Collections.unmodifiableList(stats);
    }
    
    public List<PlayerStat> getByEligiblePosition(Position pos){
        return byEligiblePosition.get(pos);
    }
    
    public List<PlayerStat> getByPositionPlayed(Position pos){
        return byPlayedPosition.get(pos);
    }
    
    private interface PlayerAttributeGetter <T>{
        public T getKey(PlayerStat player);
    }

    
}
