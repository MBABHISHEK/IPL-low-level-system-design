public class Player {

    String name;
    int age;
    String country;
    String Skill;
    int matches;
    int runs;
    int wickets;
    int ranking;
    int innings;
    int price;
    boolean isHepicked;

    Player(String name, int age, String country, String Skill, int matches, int innings, int runs, int wickets,
            int ranking, int price) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.Skill = Skill;
        this.matches = matches;
        this.runs = runs;
        this.wickets = wickets;
        this.ranking = ranking;
        this.innings = innings;
        this.price = price;
    }
}
