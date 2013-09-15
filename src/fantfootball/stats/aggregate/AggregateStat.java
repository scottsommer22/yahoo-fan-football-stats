package fantfootball.stats.aggregate;

import fantfootball.datamodel.Team;
import fantfootball.stats.aggregate.collector.AggStatType;

public class AggregateStat <T> {
    
    public static class AggregateStatKey{
        
        public static final int NO_WEEK = -1;
        
        public static final AggregateStatKey EMPTY_KEY = new AggregateStatKey(AggStatType.EMPTY, NO_WEEK);
        
        private final AggStatType type;
        private final int week;


        public AggregateStatKey(AggStatType type, int week) {
            super();
            this.type = type;
            this.week = week;
        }


        public int getWeek() {
            return week;
        }


        public AggStatType getType() {
            return type;
        }

        public String toString(){
            return type.name() + "," + week;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            result = prime * result + week;
            return result;
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            AggregateStatKey other = (AggregateStatKey) obj;
            if (type != other.type)
                return false;
            if (week != other.week)
                return false;
            return true;
        }    
        
    }
    
    private AggregateStatKey key = AggregateStatKey.EMPTY_KEY;
    private T value;
    private Team team = Team.EMPTY_TEAM;
    

    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    
    public String toString(){
        return key + "," + value;
    }

    public AggregateStatKey getKey() {
        return key;
    }
    public void setKey(AggregateStatKey key) {
        this.key = key;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AggregateStat<T> other = (AggregateStat<T>) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    
    
}
