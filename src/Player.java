public class Player {
    private String id;
    private String name;
    private String teamId;
    private PlayerRole playerRole;
    private int totalBallPlayed, total4sScored, total6sScored;
    private int totalBattingScore, bowlingWickets;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return this.teamId;
    }

    public void setTotalBallPlayed(int totalBallPlayed) {
        this.totalBallPlayed = totalBallPlayed;
    }

    public int getTotalBallPlayed() {
        return this.totalBallPlayed;
    }

    public void setTotal4sScored(int total4sScored) {
        this.total4sScored = total4sScored;
    }

    public int getTotal4sScored() {
        return this.total4sScored;
    }

    public void setTotal6sScored(int total6sScored) {
        this.total6sScored = total6sScored;
    }

    public int getTotal6sScored() {
        return this.total6sScored;
    }

    public void setTotalBattingScore(int totalBattingScore) {
        this.totalBattingScore = totalBattingScore;
    }

    public int getTotalBattingScore() {
        return this.totalBattingScore;
    }

    public void setBowlingWickets(int bowlingWickets) {
        this.bowlingWickets = bowlingWickets;
    }

    public int getBowlingWickets() {
        return this.bowlingWickets;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    public PlayerRole getPlayerRole() {
        return this.playerRole;
    }
}
