import java.util.List;
import java.util.ArrayList;

public class Match {
    int matchID;
    Team team1;
    Team team2;
    String toss;
    String result;
    double runRate;
    int firstIninningsScore;
    int secondIninningsScore;
    int firstIninningsballs;
    int secondIninningsballs;
    int firstIninningsWicket;
    int secondIninningsWicket;
    boolean firstIninningsAllout;
    boolean secondIninningsAllout;
    int[][] firstIninningsOvers;
    int[][] secondIninningsOvers;

    List<BatsMan> team1BatsMans;
    List<BatsMan> team2BatsMans;
    List<Bowler> team1Bowlers;
    List<Bowler> team2Bowlers;

    Match(int matchID, Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.matchID = matchID;
        firstIninningsOvers = new int[2][6];
        secondIninningsOvers = new int[2][6];
        team1BatsMans = new ArrayList<BatsMan>();
        team1Bowlers = new ArrayList<Bowler>();
        team2BatsMans = new ArrayList<BatsMan>();
        team2Bowlers = new ArrayList<Bowler>();

        for (Player p : team1.selectedPlayers.get("BatsMan")) {
            team1BatsMans.add(new BatsMan(0, 0, p));
        }
        for (Player p : team2.selectedPlayers.get("BatsMan")) {
            team2BatsMans.add(new BatsMan(0, 0, p));
        }

        for (Player p : team1.selectedPlayers.get("Blower")) {
            team1Bowlers.add(new Bowler(0, 0, p));
        }
        for (Player p : team2.selectedPlayers.get("Blower")) {
            team2Bowlers.add(new Bowler(0, 0, p));
        }

    }
}
