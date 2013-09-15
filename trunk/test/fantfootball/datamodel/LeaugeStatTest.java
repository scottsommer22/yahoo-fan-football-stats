package fantfootball.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import fantfootball.stats.aggregate.AggregateStat;
import fantfootball.stats.aggregate.AggregateStat.AggregateStatKey;
import fantfootball.stats.aggregate.AggregateStatCollection;
import fantfootball.stats.aggregate.MapBackedAggregateStatsCollection;
import fantfootball.stats.aggregate.collector.AggStatType;
import fantfootball.stats.aggregate.collector.NewPlayerCollector;
import fantfootball.stats.aggregate.collector.OptimalLineupCalculator;
import fantfootball.stats.aggregate.collector.PlayerUsageCollector;
import fantfootball.stats.aggregate.collector.StatCollector;
import fantfootball.stats.aggregate.collector.TeamTotalCollector;

public class LeaugeStatTest {

    Player wr1 = createPlayer("1", "Wide Receiver", "WR", "Raiders");
    Player qb1 = createPlayer("2", "QuarterBack", "QB", "Raiders");
    Player rb1 = createPlayer("3", "RUNNING Back", "RB", "Raiders");
    
    Player wr2 = createPlayer("4", "Wide Receiver2", "WR", "Bears");
    Player qb2 = createPlayer("5", "QuarterBack2", "QB", "Bears");
    Player rb2 = createPlayer("6", "RUNNING Back2", "RB", "Bears");
    
    Player te1 = createPlayer("7", "Tight End 1", "TE", "Raiders");
    Player te2 = createPlayer("8", "Tight End 2", "TE", "Bears");
    Player te3 = createPlayer("9", "Tight End 3", "TE", "Saints");
    Player te4 = createPlayer("10", "Tight End 4", "TE", "Patriots");
    
    Player wr3 = createPlayer("11", "Wide Receiver3", "WR", "Patriots");
    Player wr4 = createPlayer("12", "Wide Receiver4", "WR", "Saints");
    
    Team t1 = new Team("Team1", "Link1", 1);
    Team t2 = new Team("Team2", "Link2", 2);
    
    private static Map<Position, Integer> ROSTER_SPOTS;
    
    
    static{
        ROSTER_SPOTS = new HashMap<Position, Integer>();
        ROSTER_SPOTS.put(Position.QB, 1);
        ROSTER_SPOTS.put(Position.RB, 1);
        ROSTER_SPOTS.put(Position.WR, 1);
        ROSTER_SPOTS.put(Position.TE, 1);
        ROSTER_SPOTS.put(Position.WRT, 1);
        ROSTER_SPOTS.put(Position.K, 1);
        ROSTER_SPOTS.put(Position.DEF, 1);
    }
    
    
    @Test
    public void testStatsCollection(){
        List<PlayerStat> stats = getTestStats();
        
        LeagueStats leagueStats = LeagueStatBuilder.categorize(stats);
        
        Assert.assertNotNull(leagueStats.getWeek(1));
        Assert.assertNotNull(leagueStats.getWeek(2));
        
        Assert.assertNotNull(leagueStats.getWeek(1).getTeam( t1));
        Assert.assertNotNull(leagueStats.getWeek(1).getTeam( t2));
        
        Assert.assertNotNull(leagueStats.getWeek(2).getTeam( t1));
        Assert.assertNotNull(leagueStats.getWeek(2).getTeam( t2));
        
        PlayerStat stat = leagueStats.getWeek(1).getTeam(t1).getStats().getByPositionPlayed(Position.WR).get(0);
        Assert.assertNotNull(stat);
        Assert.assertEquals(14, stat.getPoints(), .01);
        
        stat = leagueStats.getWeek(2).getTeam(t2).getStats().getByPositionPlayed(Position.QB).get(0);
        Assert.assertNotNull(stat);
        Assert.assertEquals(10, stat.getPoints(), .01);
    }
    
    @Test
    public void testTeamSum(){
        
        List<PlayerStat> stats = getTestStats();
        LeagueStats leagueStats = LeagueStatBuilder.categorize(stats);
        
        AggregateStatCollection collected = new MapBackedAggregateStatsCollection();
        
        // Test week totals
        StatCollector sc = new TeamTotalCollector();
        sc.collect(collected, leagueStats);
        
        List<AggregateStat<?>> aggStats = collected.getAll();
        Assert.assertEquals(16, aggStats.size());
        
        AggregateStat<Double> t1wk1Total = collected.getStat(new AggregateStatKey(AggStatType.TEAM_TOTAL,1), t1);
        AggregateStat<Double> t1wk2Total = collected.getStat(new AggregateStatKey(AggStatType.TEAM_TOTAL,2), t1);
        
        Assert.assertEquals(42, t1wk1Total.getValue(),.01);
        Assert.assertEquals(10, t1wk2Total.getValue(),.01);
        
        AggregateStat<Double> t2wk1Proj = collected.getStat(new AggregateStatKey(AggStatType.TEAM_PROJECTED,1), t2);
        AggregateStat<Double> t2wk2Proj = collected.getStat(new AggregateStatKey(AggStatType.TEAM_PROJECTED,2), t2);
        
        Assert.assertEquals(48, t2wk1Proj.getValue(),.01);
        Assert.assertEquals(47, t2wk2Proj.getValue(),.01);
        
        AggregateStat<Double> t1wk1Over = collected.getStat(new AggregateStatKey(AggStatType.OVERPERFORMED,1), t1);
        AggregateStat<Double> t1wk1Under = collected.getStat(new AggregateStatKey(AggStatType.UNDERPERFORMED,1), t1);
        
        Assert.assertEquals(2, t1wk1Over.getValue(),.01);
        Assert.assertEquals(8, t1wk1Under.getValue(),.01);
        
        // Check player contribution stats
        sc = new PlayerUsageCollector();
        sc.collect(collected, leagueStats);
        
        PlayerStat wrtm1wk1 = leagueStats.getWeek(1).getTeam(t1).getStats().getByPositionPlayed(Position.WR).get(0);
        PlayerStat bntm2wk1 = leagueStats.getWeek(1).getTeam(t2).getStats().getByPositionPlayed(Position.BN).get(0);
        
        Assert.assertTrue(wrtm1wk1.isPlayed());
        Assert.assertEquals(.33333, wrtm1wk1.getPercentTotal(),.01);
        
        Assert.assertFalse(bntm2wk1.isPlayed());
        Assert.assertEquals(0, bntm2wk1.getPercentTotal(),.01);
        
        // Check new players
        sc = new NewPlayerCollector();
        sc.collect(collected, leagueStats);
        
        PlayerStat wrtm2wk2 = leagueStats.getWeek(2).getTeam(t2).getStats().getByPositionPlayed(Position.WR).get(0);
        PlayerStat tetm2wk2 = leagueStats.getWeek(2).getTeam(t2).getStats().getByPositionPlayed(Position.TE).get(0);
        
        Assert.assertTrue(wrtm2wk2.isPlayed());
        Assert.assertTrue(wrtm2wk2.isNewInStartingLineUp());
        Assert.assertTrue(wrtm2wk2.isNewToTeam());
        
        Assert.assertTrue(tetm2wk2.isPlayed());
        Assert.assertTrue(tetm2wk2.isNewInStartingLineUp());
        Assert.assertFalse(tetm2wk2.isNewToTeam());
        
        Assert.assertTrue(wrtm1wk1.isPlayed());
        Assert.assertFalse(wrtm1wk1.isNewInStartingLineUp());
        Assert.assertFalse(wrtm1wk1.isNewToTeam());
      
        sc = new OptimalLineupCalculator();
        ((OptimalLineupCalculator)sc).setRosterSpots(ROSTER_SPOTS);
        sc.collect(collected, leagueStats);
        
        PlayerStat te1wk1 = leagueStats.getWeek(1).getTeam(t1).getStats().getAllForPlayer(te1).get(0);
        PlayerStat wr3wk1 = leagueStats.getWeek(1).getTeam(t1).getStats().getAllForPlayer(wr3).get(0);
        
        Assert.assertTrue(wr3wk1.isOnOptimalLineUp());
        Assert.assertFalse(te1wk1.isOnOptimalLineUp());
        
        AggregateStat<Double> t1Optimal = collected.getStat(new AggregateStatKey(AggStatType.OPTIMAL_SCORE,1), t1);
        Assert.assertEquals(59, t1Optimal.getValue(),.01);
    }
 
    private List<PlayerStat> getTestStats(){
        List<PlayerStat> playerStats = new ArrayList<PlayerStat>();
        
        playerStats.add(createPlayerStat(1, t1, .99, wr1, 14, 12, "WR"));
        playerStats.add(createPlayerStat(1, t1, .99, qb1, 10, 12, "QB"));
        playerStats.add(createPlayerStat(1, t1, .99, rb1, 10, 12, "RB"));
        playerStats.add(createPlayerStat(1, t1, .99, te1, 8, 12, "TE"));
        playerStats.add(createPlayerStat(1, t1, .99, te2, 15, 12, "BN"));
        playerStats.add(createPlayerStat(1, t1, .99, wr3, 10, 12, "BN"));

        playerStats.add(createPlayerStat(1, t2, .99, wr2, 14, 12, "WR"));
        playerStats.add(createPlayerStat(1, t2, .99, qb2, 10, 12, "QB"));
        playerStats.add(createPlayerStat(1, t2, .99, rb2, 10, 12, "RB"));
        playerStats.add(createPlayerStat(1, t2, .99, te3, 8, 12, "TE"));
        playerStats.add(createPlayerStat(1, t2, .99, te4, 15, 12, "BN"));
        playerStats.add(createPlayerStat(1, t2, .99, wr4, 20, 12, "BN"));
        
        playerStats.add(createPlayerStat(2, t1, .99, wr1, 1, 12, "WR"));
        playerStats.add(createPlayerStat(2, t1, .99, qb1, 2, 12, "QB"));
        playerStats.add(createPlayerStat(2, t1, .99, rb1, 3, 12, "RB"));
        playerStats.add(createPlayerStat(2, t1, .99, te1, 4, 12, "TE"));
        playerStats.add(createPlayerStat(2, t1, .99, te2, 5, 12, "BN"));
        playerStats.add(createPlayerStat(2, t1, .99, wr4, 6, 12, "BN"));

        playerStats.add(createPlayerStat(2, t2, .99, wr2, 14, 12, "BN"));
        playerStats.add(createPlayerStat(2, t2, .99, qb2, 10, 12, "QB"));
        playerStats.add(createPlayerStat(2, t2, .99, rb2, 10, 12, "RB"));
        playerStats.add(createPlayerStat(2, t2, .99, te3, 8, 12, "BN"));
        playerStats.add(createPlayerStat(2, t2, .99, te4, 15, 11, "TE"));
        playerStats.add(createPlayerStat(2, t2, .99, wr3, 20, 12, "WR"));
        
        return playerStats;
    }
    
    private Player createPlayer(String key, String name, String position, String team){     
        Player p1 = new Player();
        p1.setKey(key);
        p1.setName(name);
        p1.setPosition(Position.fromTag(position));
        p1.setTeam(team);
        return p1;
    }
    
    private PlayerStat createPlayerStat(int week, Team t, double start, Player player, double points, double proj, String position){
        PlayerStat p1 = new PlayerStat(week, t);
        p1.setPerctStart(start);
        p1.setPlayer(player);
        p1.setPoints(points);
        p1.setProjectedPoints(proj);
        p1.setPosition(Position.fromTag(position));
        return p1;
    }
}
