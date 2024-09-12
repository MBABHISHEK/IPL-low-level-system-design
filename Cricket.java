import java.util.*;

public class Cricket {

}

class IPL {

    List<Team> teams;
    List<Player> players;
    List<Match> matches;
    Database db;
    HashMap<String, List<Player>> playersBasedOnSkill;

    IPL() {
        db = new Database();
        matches = new ArrayList<>();
        teams = new ArrayList<Team>();
        players = new ArrayList<Player>();
        playersBasedOnSkill = new HashMap<>();
        playersBasedOnSkill.put("AR", new ArrayList<>());
        playersBasedOnSkill.put("BT", new ArrayList<>());
        playersBasedOnSkill.put("BL", new ArrayList<>());
        playersBasedOnSkill.put("WK", new ArrayList<>());
    };

    void registerPlayer() {
        String[] line = db.data.split("\n");

        for (int i = 0; i < line.length; i++) {
            String[] attributes = line[i].split(",");
            String name = attributes[0].trim();
            int age = Integer.parseInt(attributes[1].trim());
            String country = attributes[2].trim();
            String skill = attributes[3].trim();
            int matches = Integer.parseInt(attributes[4].trim());
            int innings = Integer.parseInt(attributes[5].trim());
            int runs = Integer.parseInt(attributes[6].trim());
            int wickets = Integer.parseInt(attributes[7].trim());
            int ranking = Integer.parseInt(attributes[8].trim());
            int price = Integer.parseInt(attributes[9].trim());
            players.add(new Player(name, age, country, skill, matches, innings, runs, wickets, ranking, price));
        }
    }

    void createSkillMap() {
        for (int i = 0; i < players.size(); i++) {
            String role = players.get(i).Skill;
            playersBasedOnSkill.get(role).add(players.get(i));
        }
    }

    void sortPlayers() {
        for (String skill : playersBasedOnSkill.keySet()) {
            List<Player> playersList = playersBasedOnSkill.get(skill);
            // Sort the list of players based on their price
            playersList.sort(Comparator.comparingInt(p -> p.price));
            playersBasedOnSkill.put(skill, playersList);
        }
    }

    void sortTeam() {
        Collections.sort(teams, new Comparator<Team>() {
            public int compare(Team t1, Team t2) {
                return Integer.compare(t1.budget, t2.budget);
            }
        });
    }

    public static int nextBallScore() {
        int score = 0;
        Random random = new Random();

        int[] outcomes = { -1, 0, 1, 2, 3, 4, 6 };
        score = outcomes[random.nextInt(outcomes.length)];
        return score;

        // alterate - match fixing ;-(
        // Match1 :
        // CSK:
        // Over1:2,4,0,-1,1,2
        // Over2:6,-1
        // Total : 15 Runs All out 1.2 overs
        // MI:
        // Over1:2,4,6,2,-1,4
        // Total: 18/1 in 1 over.
        // CSK Net Run Rate = (15 runs/12 balls)*6 balls per over - (18 runs/6 balls)*6
        // balls per over = 7.5 - 18 = -11.5
        // MI Net Run Rate = +11.5

        // Match 2: CSK-RCB ; CSK 28 all out in 9 balls ; RCB: 28 all out in 12 balls
        // Match 3: MI-RCB: MI 19 all out in 7 balls; RCB 17/1 in 12 balls
        // static int nextBall = 0;
        // int outcomes[]={/CSK-MI/
        // /* CSK Over1 */2,4,0,-1,1,2,
        // /* CSK Over2 */6,-1,
        // /*MI Over1 */2,4,6,2,-1,4,
        // /CSK-RCB/
        // /* CSK Over1 */3,1,6,4,2,-1,
        // /* CSK Over2 */6,6,-1,
        // /*RCB Over1 */6,6,1,1,3,-1,
        // /*RCB Over1 */6,2,1,1,1,-1,
        // /"MI","RCB"/
        // /*MI Over1 */2,4,6,4,-1,3,
        // /*MI Over2 */-1,
        // /*RCB Over1 */6,2,1,0,3,-1,
        // /*RCB Over2 */1,2,1,0,0,1,
        // };
        // //return(outcomes[nextBall++]);

    };

    void addTeam(String name, int budget, String playerMixStrategy) {

        String[] values = playerMixStrategy.split(";");
        int[] skill_values = new int[4];
        for (int i = 0; i < values.length; i++) {
            String[] skills = values[i].split(":");
            skill_values[i] = Integer.parseInt(skills[1]);
        }
        teams.add(new Team(name, budget, skill_values[0], skill_values[1], skill_values[2], skill_values[3]));

        // put the definition here

        // Skills are coded as BT, BL, AR or WK
        // playerMixStategy is each team's decision to spend per skill. e.g.,
        // AR:25;BT:15;WK:40;BL:20";
        // The numbers attached to the skill is the % of the budget, the team is willing
        // to spend on each skill.

    }

    int doPlayerPick() {
        // put the definition here

        // Player pool database below; use this raw data to create an efficiant data
        // structure.

        /*
         * Each Team
         * Will need total 4 players – one of each skill. Each team has a given custom
         * budget to spend.
         * 
         * 
         * will register a playerMixStrategy of 4 players and their priority like
         * BT:20,BL:20,AR:35,WK:25 etc.; now,
         * each team may have a different need and priority.
         * The numbers attached to the skill is the % of the budget, the team is willing
         * to spend on each skill.
         * 
         * Player pick rules:
         * 
         * Each team will get a chance to fulfil one player need at a time, and then
         * chance goes to next team and so on until all needed players are picked.
         * The team with lowest initial budget goes first.
         * Team is expected to pick the closest matching priced player to the available
         * budget.
         * Team will not overspand on any player with reference to the playerMixStrategy
         * allocated budget of the skill
         * 
         */

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < teams.size(); j++) {
                Team team = teams.get(j);
                String role = team.skillsList.get(i).getKey();
                Double value = team.skillsList.get(i).getValue();
                List<Player> playersFromSkills = playersBasedOnSkill.get(role);
                for (int k = playersFromSkills.size() - 1; k >= 0; k--) {
                    Player p1 = playersFromSkills.get(k);
                    double price = (double) p1.price;
                    if (!p1.isHepicked && value >= price) {
                        team.players.add(p1);
                        p1.isHepicked = true;
                        break;
                    }
                }

            }
        }
        return 0;
    }

    void firstIninnings(Match match) {

        match.firstIninningsScore = 0;
        List<BatsMan> btList = match.team1BatsMans;
        BatsMan striker = btList.get(0);
        BatsMan nonStriker = btList.get(1);
        match.firstIninningsWicket = 0;
        int batting_order = 1;
        System.out.printf("Batting Team: Striker: %-15s Non-striker: %-15s\n", striker.player.name,
                nonStriker.player.name);

        for (int j = 0; j < 2; j++) {
            Bowler b = match.team2Bowlers.get(j);
            System.out.printf("Bowling Team: Bowler: %-15s\n", b.player.name);

            System.out.printf("%-10s %-15s %-15s %-10s\n", "Over", "Striker", "Bowler", "Runs");

            for (int i = 0; i < 6 && !match.firstIninningsAllout; i++) {
                int run = nextBallScore();
                match.firstIninningsballs++;
                match.firstIninningsOvers[0][i] = run;
                b.ballsBowled++;
                striker.ballsFaced++;
                System.out.printf("%-10s %-15s %-15s %-10s\n", (j + 1) + "." + (i + 1), striker.player.name,
                        b.player.name, (run == -1 ? "OUT" : run));

                if (run == -1) {
                    System.out.printf(" ****  %-10s %-10s %-20s****\n", "Out!", "Batsman: " + striker.player.name,
                            "Bowler: " + b.player.name);
                    b.wickets++;
                    match.firstIninningsWicket++;
                    if (match.firstIninningsWicket == 2) {
                        match.firstIninningsAllout = true;
                        System.out.printf("\n%s All out!\n", match.team1.name);
                        break;
                    }
                    batting_order++;
                    striker = btList.get(batting_order);
                } else {
                    match.firstIninningsScore += run;
                    b.runsgiven += run;
                    striker.runs += run;
                    if (run % 2 == 1) {
                        BatsMan demo = striker;
                        striker = nonStriker;
                        nonStriker = demo;
                    }
                }
            }

            BatsMan demo = striker;
            striker = nonStriker;
            nonStriker = demo;
        }

    }

    void secondIninnings(Match match) {

        match.secondIninningsScore = 0;
        List<BatsMan> btList = match.team2BatsMans;
        BatsMan striker = btList.get(0);
        BatsMan nonStriker = btList.get(1);
        match.secondIninningsWicket = 0;
        int batting_order = 1;
        System.out.printf("%-20s %-20s %-20s\n", "Batting Team", "Striker", "Non-Striker");
        System.out.printf("%-20s %-20s %-20s\n", match.team2.name, striker.player.name, nonStriker.player.name);

        for (int j = 0; j < 2; j++) {
            Bowler b = match.team1Bowlers.get(j);
            System.out.printf("\n%-20s %-20s\n", "Bowling Team", "Bowler: " + b.player.name);
            System.out.printf("%-10s %-15s %-15s %-10s\n", "Over", "Batsman", "Bowler", "Runs");

            for (int i = 0; i < 6 && !match.secondIninningsAllout; i++) {
                match.secondIninningsballs++;
                int run = nextBallScore();
                match.secondIninningsOvers[j][i] = run;
                b.ballsBowled++;
                System.out.printf("%-10s %-15s %-15s %-10s\n", (j + 1) + "." + (i + 1), striker.player.name,
                        b.player.name, (run == -1 ? "OUT" : run));

                if (run == -1) {
                    System.out.printf("****  %-10s %-10s %-20s****\n", "Out!", "Batsma: " + striker.player.name,
                            "Bowler: " + b.player.name);
                    b.wickets++;
                    match.secondIninningsWicket++;

                    if (match.secondIninningsWicket == 2) {
                        match.secondIninningsAllout = true;
                        System.out.printf("%-20s\n", match.team2.name + " all out!");
                        return;
                    }

                    batting_order++;
                    striker = btList.get(batting_order);
                } else {
                    match.secondIninningsScore += run;
                    striker.runs += run;
                    b.runsgiven += run;

                    if (match.firstIninningsScore < match.secondIninningsScore) {
                        return;
                    }

                    if (run % 2 == 1) {
                        BatsMan demo = striker;
                        striker = nonStriker;
                        nonStriker = demo;
                    }
                }
            }
        }
    }

    void playMatch(int matchId, String team1, String team2) {

        Team t1 = null;
        Team t2 = null;
        for (Team team : teams) {
            if (team.name == team1) {
                t1 = team;
                t1.matchesPlayed++;
            }
            if (team.name == team2) {
                t2 = team;
                t2.matchesPlayed++;
            }
        }

        Match match = new Match(matchId, t1, t2);
        match.toss = team1;

        System.out.println("First ininnings Started: " + team1 + " Is batting " + team2 + " Is bowling ");
        firstIninnings(match);
        System.out.println("First ininnings score" + match.firstIninningsScore + "\n");
        System.out.println("Second ininnings Started: " + team2 + " Is batting " + team1 + " Is bowling \n");
        secondIninnings(match);
        System.out.println("Second ininnings score " + match.secondIninningsScore);
        if (match.firstIninningsScore > match.secondIninningsScore) {
            System.out
                    .println(
                            team1 + " Won the match " + " by "
                                    + (match.firstIninningsScore - match.secondIninningsScore)
                                    + " Runs ");
            match.team1.points = +2;
            match.team1.won++;
            match.team2.lost++;

        } else {
            System.out.println(team2 + " Won the match " + " by " + (2 - match.secondIninningsWicket) + " Wickets ");
            match.team2.points = +2;
            match.team2.won++;
            match.team1.lost++;
        }
        matches.add(match);
        match.team2.ballsBowled += match.firstIninningsballs;
        match.team1.ballsPlayed += match.firstIninningsballs;
        match.team2.ballsPlayed += match.secondIninningsballs;
        match.team1.ballsBowled += match.secondIninningsballs;
        match.team1.runsBy += match.firstIninningsScore;
        match.team2.runsBy += match.secondIninningsScore;
        match.team1.runsAgainst += match.secondIninningsScore;
        match.team2.runsAgainst += match.secondIninningsScore;
        match.team1.updateNRR();
        match.team2.updateNRR();

        // Put the definition here

        // 1 innings each per team
        // 2 overs per game of 6 balls; 1 over per any two bowlers
        // 3 batter per inning; inning gets over if 2 wickets fall or required score is
        // reached.
        // Each ball bowled can result in (-1, 0,1,2,3,4 or 6); -1 indicates wicket.
        // Use provided funtion nextBallScore() to random generate a ball outcome
        // batsman switches between striking to non-striking either when scoring odd
        // number of runs or when over changes.

        // add some output to indicate a running commentary per ball to verify the
        // output.
        // e.g.,Batting Team1 Name
        // Over #: Ball #: Batsman, Bowler, Ball outcome - for every ball
        //
        // Bowling Team2 Name:
        // Over #: Bowler: Run Given : Wickets
        // Final Team 1 Score

        // Batting Team2 Name
        // Over #: Ball #: Batsman, Bowler, Ball outcome - for every ball
        //
        // Bowling Team1 Name:
        // Over #: Bowler: Run Given : Wickets
        // Final Team2 Score
        // Final result.
    }

    void printMatchDetails(int matchID) {

        Match match = null;
        for (Match m : matches) {
            if (Integer.compare(matchID, m.matchID) == 0) {
                match = m;
            }
        }

        System.out.println();
        System.out.println(
                "********************************   SCORE CARD **********************************");
        System.out.println("\n*****************************First Innings Details**************************");
        System.out.printf("Total Score: %d/%d\n", match.firstIninningsScore, match.firstIninningsWicket);
        System.out.println("Batting (" + match.team1.name + "):");
        for (BatsMan b : match.team1BatsMans) {
            System.out.printf("Batter %-15s | Balls faced: %-5d | Runs: %-5d\n",
                    b.player.name, b.ballsFaced, b.runs);
            b.updateRuns();
        }
        System.out.println("Bowling (" + match.team2.name + "):");
        for (Bowler b : match.team2Bowlers) {
            System.out.printf("Bowler %-15s | Balls Bowled: %-5d | Runs Given: %-5d | Wickets: %-5d\n",
                    b.player.name, b.ballsBowled, b.runsgiven, b.wickets);
            b.updateWickets();
        }

        System.out.println("\n******************Second Innings Details**************************");
        System.out.printf("Total Score: %d/%d\n", match.secondIninningsScore, match.secondIninningsWicket);
        System.out.println("Batting (" + match.team2.name + "):");
        for (BatsMan b : match.team2BatsMans) {
            System.out.printf("Batter %-15s | Balls faced: %-5d | Runs: %-5d\n",
                    b.player.name, b.ballsFaced, b.runs);
            b.updateRuns();
        }
        System.out.println("Bowling (" + match.team1.name + "):");
        for (Bowler b : match.team1Bowlers) {
            System.out.printf("Bowler %-15s | Balls Bowled: %-5d | Runs Given: %-5d | Wickets: %-5d\n",
                    b.player.name, b.ballsBowled, b.runsgiven, b.wickets);
            b.updateWickets();
        }

        if (match.firstIninningsScore > match.secondIninningsScore) {
            System.out.printf("%s won the match by %d runs\n", match.team1.name,
                    match.firstIninningsScore - match.secondIninningsScore);
        } else {
            System.out.printf("%s won the match by %d wickets\n", match.team2.name,
                    (2 - match.secondIninningsWicket));
        }
        // Put the definition here
        /*
         * Create a score sheet of the format below for each Innings
         * 
         * Batting (Team Name):
         * Batter1 Name : Balls faced: Runs
         * Batter2 Name : Balls faced: Runs
         * Batter3 Name : Balls faced: Runs
         * 
         * Bowling (Team Name):
         * Bowler1 Name: Balls Bowled: Runs Given: Wickets
         * Bowler2 Name: Balls Bowled: Runs Given: Wickets
         * 
         * Total Score: Runs / Balls Faced.
         * Match Result
         * 
         */
    }

    void printTeamDetails() {
        // Put the definition here
        // Create a table with Team stats with format
        // Team Name: Matches Played: Won : Lost : Points : Runs By: Balls Played: Runs
        // Against: Balls bowled: NRR

        // The Net run rate is calculated by deducting the average runs per over scored
        // against a team from the average runs an over scored by a team in a
        // tournament.

        // Notably, if a team has chased a target in less than 3 overs (for say 1.3
        // overs), then 1.3 overs ,i.e., 6+3=9 balls will be considered and not the
        // actual 12.
        // But if a team has been bowled out in less than 2 overs (for say 1.3) then 12
        // will be considered and not 9 balls played.

        // Points: 2 for a win; 0 for a loss; 1 for a tie; If Tie, means scores are
        // level irrespective of wickets lost, No super over.

        System.out.println();
        System.out.println(
                "*************************************************************  POINTS TABLE  **************************************************");
        System.out.printf("%-15s| %-15s| %-10s| %-10s| %-10s| %-15s| %-15s| %-15s| %-15s| %-10s|\n",
                "Team Name", "Matches Played", "Won", "Lost", "Points", "Runs By",
                "Balls Played", "Runs Against", "Balls Bowled", "NRR");
        for (Team team : teams) {
            System.out.printf("%-15s| %-15d| %-10d| %-10d| %-10d| %-15d| %-15d| %-15d| %-15d| %-10.2f|\n",
                    team.name, team.matchesPlayed, team.won, team.lost, team.points,
                    team.runsBy, team.ballsPlayed, team.runsAgainst, team.ballsBowled, team.NRR);
        }

    }

    void printPlayerCareerDetails() {
        // Put the definition here
        // Create a table with Team stats with format
        // Player Name: Matches Played: Runs Scored: Wickets

        System.out.println();
        System.out.println(
                "*************************************************************  CAREER DETAILS **************************************************");
        System.out.printf("%-20s| %-20s| %-20s| %-20s\n", "Player Name", "Matches Played", "Runs Scored", "Wickets");

        for (Player p : players) {
            System.out.printf("%-20s| %-20d| %-20d| %-20d|\n",
                    p.name, p.matches, p.runs, p.wickets);
        }

    }

    public static void main(String[] args) {
        IPL ipl = new IPL();

        System.out.println("Hello IPL 2024!!\n");

        System.out.println("Register the player for the Auctions");
        // this method is used to register all players
        ipl.registerPlayer();

        // this method is used to create hashtable based on the skills for the
        // efficienct retrieve
        ipl.createSkillMap();

        // this method is used to create to sort the players based on the prices and
        // skills
        ipl.sortPlayers();

        System.out.println("Register the Teams for the Auctions");

        // Adding teams
        ipl.addTeam("RCB", 105, "AR:25;BT:40;WK:15;BL:20");
        ipl.addTeam("CSK", 100, "AR:25;BT:15;WK:40;BL:20");
        ipl.addTeam("MI", 110, "ÄR:25;BT:15;WK:20;BL:40");

        // team pick players

        // sort the teams by budget
        ipl.sortTeam();

        System.out.println("Auction Started");
        ipl.doPlayerPick();

        // this is arrange the players based on the skills
        for (Team team : ipl.teams) {
            team.updateSelectedPlayer();
        }

        // Printing team details
        ipl.printTeamDetails();
        ipl.printPlayerCareerDetails();

        ipl.playMatch(202401, "CSK", "MI");
        // Printing match details
        ipl.printMatchDetails(202401);

        // Printing team details
        ipl.printTeamDetails();
        ipl.printPlayerCareerDetails();

        ipl.playMatch(202402, "CSK", "RCB");
        // Printing match details
        ipl.printMatchDetails(202402);
        // Printing team details
        ipl.printTeamDetails();
        ipl.printPlayerCareerDetails();

        ipl.playMatch(202403, "MI", "RCB");
        // Printing match details
        ipl.printMatchDetails(202403);
        // Printing team details
        ipl.printTeamDetails();
        ipl.printPlayerCareerDetails();

    }
}
