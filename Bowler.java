public class Bowler {
    int ballsBowled;
    int runsgiven;;
    int wickets;
    Player player;

    Bowler(int ballsBowled, int wickets, Player player) {
        this.ballsBowled = ballsBowled;
        this.wickets = wickets;
        this.player = player;
        runsgiven = 0;
    }

    void updateWickets() {
        player.wickets += wickets;
        if ("BL".equals(player.Skill)) {
            player.matches++;
            player.innings++;
        }

    }

}
