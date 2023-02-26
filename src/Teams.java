import java.util.ArrayList;

public class Teams {
    private String teamId;
    private String name;
    private int score;
    private int wickets;
    ArrayList<ArrayList<Integer>> scorePerOver;
    ArrayList<Player> players = new ArrayList<>();
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return this.score;
    }
    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getWickets() {
        return wickets;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    public void setScorePerOver(ArrayList<ArrayList<Integer>> scorePerOver){
        this.scorePerOver=scorePerOver;
    }
    public ArrayList<ArrayList<Integer>> getScorePerOver(){
        return this.scorePerOver;
    }
}
