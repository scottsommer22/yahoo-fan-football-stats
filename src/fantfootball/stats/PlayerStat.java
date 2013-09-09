package fantfootball.stats;

/**
 * Java bean representing the stats for a player for a particular week.
 */
public class PlayerStat {
    int week;
    Player player = new Player();
    String position;
    double points;
    double projectedPoints;
    String manager;
    double perctStart;

    public PlayerStat(int wk, String mgrName) {
        week = wk;
        manager = mgrName;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    
    public String toString() {
        return week + "," + manager + ",\"" + player.getName() + "\"," + position + "," + points + "," 
                + player.getTeam() + "," + player.getPosition()+ "," + projectedPoints+ "," + perctStart;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public double getProjectedPoints() {
        return projectedPoints;
    }

    public void setProjectedPoints(double projectedPoints) {
        this.projectedPoints = projectedPoints;
    }

    public double getPerctStart() {
        return perctStart;
    }

    public void setPerctStart(double perctStart) {
        this.perctStart = perctStart;
    }


}
