package Controller;


import Dao.MatchDao;
import Dao.PlayerDao;
import Dao.TeamDao;
import Enum.*;
import Model.Player;
import Model.Team;
import Utility.ScoreBoard;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MatchController {
    private MatchDao matchDao;
    private PlayerDao playerDao;
    private TeamDao teamDao;

    Scanner sc = new Scanner(System.in);

    public MatchController() {
        matchDao = new MatchDao();
        playerDao = new PlayerDao();
        teamDao = new TeamDao();
    }

    public void start()  {
        System.out.println("Enter the number of overs ");
        int over = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the name of Team1 ");
        String team1Name = sc.nextLine();
        String team1Id = teamDao.addTeam(team1Name);

        System.out.println("Enter the name of Team2 ");
        String team2Name = sc.nextLine();
        String team2Id = teamDao.addTeam(team2Name);
        addTeamPlayer(team1Id, team2Id);
        String matchId= matchDao.addMatch(team1Id, team2Id, over);
        String battingId = toss(team1Id, team2Id);
        String bowlingId;
        if (battingId == team1Id) bowlingId = team2Id;
        else bowlingId = team1Id;
        teamDao.updateStatus(battingId,TeamStatus.BATTING.name());
        teamDao.updateStatus(bowlingId,TeamStatus.BOWLING.name());
        int target= matchSimulator(battingId, over,0);
        matchSimulator(bowlingId, over, target+1);
        ScoreBoard scoreBoard=new ScoreBoard();
        scoreBoard.teamScore(matchId);
    }

    public void addTeamPlayer(String team1Id, String team2Id) {
        String p1 = playerDao.addPlayer("Rohit", PlayerRole.BATSMAN,team1Id);
        String p2 = playerDao.addPlayer("Virat", PlayerRole.BATSMAN,team1Id);
        String p3 = playerDao.addPlayer("Ravindra", PlayerRole.BOWLER,team1Id);
        String p4 = playerDao.addPlayer("Ishaan", PlayerRole.BOWLER,team1Id);
        List<String> team1Players = new ArrayList<>();
        team1Players.add(p1);
        team1Players.add(p2);
        team1Players.add(p3);
        team1Players.add(p4);
        teamDao.updatePlayerList(team1Id, team1Players);
        String p5 = playerDao.addPlayer("Mohammad", PlayerRole.BATSMAN,team2Id);
        String p6 = playerDao.addPlayer("Shahid", PlayerRole.BATSMAN,team2Id);
        String p7 = playerDao.addPlayer("Shohiab", PlayerRole.BOWLER,team2Id);
        String p8 = playerDao.addPlayer("Imran", PlayerRole.BOWLER,team2Id);
        List<String> team2Players = new ArrayList<>();
        team2Players.add(p5);
        team2Players.add(p6);
        team2Players.add(p7);
        team2Players.add(p8);
        teamDao.updatePlayerList(team2Id, team2Players);
    }

    public String toss(String team1Id, String team2Id) {
        int toss = (int) (Math.random() * 2);
        Team team1 = teamDao.getTeam(team1Id);
        Team team2 = teamDao.getTeam(team2Id);
        System.out.println(toss == 0 ? team1.getName() + " has won the toss " : team2.getName() + " has won the toss ");
        System.out.println("Enter 1 to do the batting and 2 for bowling :");
        int decision = sc.nextInt(); //1
        String result = (decision == 1) ? "batting" : "bowling";
        System.out.println((toss == 0 ? (team1.getName()) : (team2.getName())) + " decided to do " + result);
        return ((toss == 0 && decision == 1) || (toss == 1 && decision == 2)) ? team1Id : team2Id;
    }

    public int matchSimulator(String teamId, int overSize,int target)  {
        int total4s = 0;
        int total6s = 0;
        int ballPlayed = 0;
        int totalScore = 0;
        int wickets = 0;
        int teamScore = 0;
        Team team = teamDao.getTeam(teamId); // return the team
        int size = team.getPlayers().size();  // size of the team
        List<String> team1Player = team.getPlayers(); // Fetching the playerIds
        List<List<Integer>> scorePerOver = new ArrayList<>();
        String p = team1Player.get(wickets);
        for (int over = 0; over < overSize && wickets < size - 1 ; over++) {
            ArrayList<Integer> runPerOver = new ArrayList<>();
            for (int ball = 0; ball < 6 && wickets < size - 1 ; ball++) {
                int runPerBall = runsMadeByPlayer(p);
                ballPlayed++;
                runPerOver.add(runPerBall);
                if (runPerBall == 0) {
                    wickets++;
                    playerDao.updateScore(p, total4s, total6s, ballPlayed, totalScore);
                    total4s = 0;
                    total6s = 0;
                    ballPlayed = 0;
                    totalScore = 0;
                    p = team1Player.get(wickets);
                } else {
                    if (runPerBall == 4) total4s++;
                    else if (runPerBall == 6) total6s++;
                    totalScore += runPerBall;
                    teamScore += runPerBall;
                }
                if(team.getTeamStatus().equals(TeamStatus.BOWLING.name()) && teamScore>target) break;
            }
            playerDao.updateScore(p, total4s, total6s, ballPlayed, totalScore);
            scorePerOver.add(runPerOver);
            if(team.getTeamStatus().equals(TeamStatus.BOWLING.name()) && teamScore>target) break;
        }
        team.setScorePerOver(scorePerOver);
        team.setWickets(wickets);
        team.setScore(teamScore);
        teamDao.setTeamScore(team);
        return teamScore;
    }

    public int runsMadeByPlayer(String p)  {
        Random r=new Random();
        Player player=playerDao.getPlayer(p);
        int probability=r.nextInt(100);
        int run=r.nextInt(7);
        if(player.getPlayerRole().equals(PlayerRole.BATSMAN.name())){
          return probability<60? 6: run;
        }
        else{
          return probability<60? 0:run;
        }
    }
}
