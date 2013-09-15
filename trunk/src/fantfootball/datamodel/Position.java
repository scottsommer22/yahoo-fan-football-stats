package fantfootball.datamodel;

import java.util.HashMap;
import java.util.Map;

public enum Position {
    QB("QB"),RB("RB"),TE("TE"),WR("WR"),DEF("DEF"),K("K"),WRT("W/R/T"),BN("BN");
    
    private final String tag;
   
    private static final Map<String, Position> POSITION_MAP = new HashMap<String, Position>();
    
    static{
        for(Position p : Position.values()){
            POSITION_MAP.put(p.getTag(), p);
        }
    }
    
    private Position(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
    
   public static Position fromTag(String tag){
       return POSITION_MAP.get(tag);
   }
   
   public String toString(){
       return tag;
   }
    
}
