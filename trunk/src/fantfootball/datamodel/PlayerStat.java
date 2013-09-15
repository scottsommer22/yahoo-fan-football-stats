package fantfootball.datamodel;


/**
 * Java bean representing the stats for a player for a particular week.
 */
public class PlayerStat {
    private int week;
    private Player player = new Player();
    private Position position;
    private double points;
    private double projectedPoints;
    private Team team;
    private double perctStart;
    private boolean newToTeam = false;
    private boolean newInStartingLineUp = false;
    private boolean onOptimalLineUp = false;
    private double percentTotal = 0;
    private boolean played = false;

    public PlayerStat(int wk, Team team) {
        week = wk;
        this.team = team;
    }

    public int getWeek() {
        return week;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
    
    public String toString() {
        return week + "," + team + "," 
                + player + ","+ position + "," + points + "," 
                + projectedPoints+ "," + perctStart + "," + newToTeam+ "," +newInStartingLineUp + ","+ onOptimalLineUp + ","
                + played + "," + percentTotal;
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

    public boolean isNewToTeam() {
        return newToTeam;
    }

    public void setNewToTeam(boolean newToTeam) {
        this.newToTeam = newToTeam;
    }

    public boolean isOnOptimalLineUp() {
        return onOptimalLineUp;
    }

    public void setOnOptimalLineUp(boolean onOptimalLineUp) {
        this.onOptimalLineUp = onOptimalLineUp;
    }

    public Team getTeam() {
        return team;
    }

    public double getPercentTotal() {
        return percentTotal;
    }

    public void setPercentTotal(double percentTotal) {
        this.percentTotal = percentTotal;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isNewInStartingLineUp() {
        return newInStartingLineUp;
    }

    public void setNewInStartingLineUp(boolean newInStartingLineUp) {
        this.newInStartingLineUp = newInStartingLineUp;
    }


}
