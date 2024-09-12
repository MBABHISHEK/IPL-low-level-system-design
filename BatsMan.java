public class BatsMan {
    Player player;
    int ballsFaced;
    int runs;
    boolean out;

    BatsMan(int ballsFaced, int runs, Player player) {
        this.ballsFaced = ballsFaced;
        this.runs = runs;
        this.player = player;
    }

    void updateRuns() {
        player.runs += runs;
        player.innings++;
        player.matches++;
    }
}
