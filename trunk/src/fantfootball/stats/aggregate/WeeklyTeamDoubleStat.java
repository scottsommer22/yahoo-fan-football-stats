package fantfootball.stats.aggregate;

import fantfootball.datamodel.Team;
import fantfootball.stats.aggregate.collector.AggStatType;

public class WeeklyTeamDoubleStat extends AggregateStat<Double> {

    
    public WeeklyTeamDoubleStat(AggStatType type, Team team, int week){
        super();
        AggregateStatKey key = new AggregateStatKey(type, week);
        super.setKey(key);
        super.setTeam(team);
        super.setValue(new Double(0));
    }
    
    public void add(double value){
        super.setValue(super.getValue() + value);
    }
    
}
