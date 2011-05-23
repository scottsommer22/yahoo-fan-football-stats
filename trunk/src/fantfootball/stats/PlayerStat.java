package fantfootball.stats;

/**
 * Java bean representing the stats for a player for a particular week.
 */
public class PlayerStat {
    int week;
    String player;
    String position;
    String points;
    String manager;

    public PlayerStat(int wk, String name) {
        week = wk;
        manager = name;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String toString() {
        return week + "," + manager + ",\"" + player + "\",\"" + position + "\"," + points;
    }

}
