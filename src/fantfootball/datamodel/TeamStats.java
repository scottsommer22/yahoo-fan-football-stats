package fantfootball.datamodel;


public class TeamStats {

    private Team team;
    
    private StatsCollection stats = new StatsCollection();

    public TeamStats(Team team) {
        super();
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }


    public StatsCollection getStats() {
        return stats;
    }

    public void addPlayerStat(PlayerStat playerStat) {
        stats.addPlayerStat(playerStat);
    }

}
