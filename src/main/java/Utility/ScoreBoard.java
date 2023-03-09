package Utility;
import Dao.MatchDao;
import Dao.PlayerDao;
import Dao.TeamDao;
import Model.Player;
import Model.Team;
import com.mongodb.client.MongoCollection;
import Enum.*;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    private PlayerDao playerDao;
    private TeamDao teamDao;
    private MatchDao matchDao;
    public ScoreBoard(){
        playerDao=new PlayerDao();
        teamDao=new TeamDao();
        matchDao=new MatchDao();
    }

    public void teamScore(String matchId) {
        String team1Id= matchDao.findMatchById(matchId).getTeam1Id();
        String team2Id=matchDao.findMatchById(matchId).getTeam2Id();
        Team team1=teamDao.getTeam(team1Id);
        Team team2=teamDao.getTeam(team2Id);
        scorePerOver(team1);
        scorePerOver(team2);
        scoreBoard(team1,team2);
    }
    public void scoreBoard(Team team1, Team team2) {
        System.out.println("************************************ Match Result ************************************");
        System.out.println("Total score of " + team1.getName() + " " + team1.getScore() + " runs at wickets " + team1.getWickets() + "/"+team1.getPlayers().size());
        System.out.println("Total score of " + team2.getName() + " " + team2.getScore() + " runs at wickets " + team2.getWickets() + "/"+team2.getPlayers().size());
        if (team1.getScore() > team2.getScore()) {
            System.out.println(team1.getTeamStatus().equals(TeamStatus.BATTING.name())? team1.getName() + " has won the match by " + (team1.getScore() - team2.getScore()) + " runs ":
                    team1.getName() + " has won the match by " + (team1.getPlayers().size()-team1.getWickets()) + " wickets "  );
            saveTeamStatus(team1,team2,false);
        } else if (team1.getScore() == team2.getScore()) {
            System.out.println("Tie between " + team1.getName() + " and " + team2.getName());
            saveTeamStatus(team1,team2,true);
        } else {
            System.out.println(team2.getTeamStatus().equals(TeamStatus.BATTING.name())? team2.getName() + " has won the match by " + (team2.getScore() - team1.getScore()) + " runs ":
                    team2.getName() + " has won the match by " + (team2.getPlayers().size()-team2.getWickets() ) + " wickets " );
            saveTeamStatus(team2,team1,false);
        }

    }
    public void scorePerOver(Team team){
        System.out.println("************************************Score Of " + team.getName() + " ************************************");
        int c = 1;
        for (List<Integer> over : team.getScorePerOver()) {
            System.out.println("In Over : " + c);
            for (int run : over) {
                if (run == 0)
                    System.out.print("W ");
                else
                    System.out.print(run + " ");
            }
            c++;
            System.out.println();
            System.out.println();
        }
        playerScoreBoard(team.getPlayers());
    }
    public void playerScoreBoard(List<String> playerIds){
        for (String id : playerIds) {
            Player p=playerDao.getPlayer(id);
            if (p.getTotalBattingScore() > 0 || p.getTotalBallPlayed() > 0) {
                System.out.println("Runs made by " + p.getName() + " : " + p.getTotalBattingScore());
                System.out.println("Total 4s made by " + p.getName() + " : " + p.getTotal4sScored());
                System.out.println("Total 6s made by " + p.getName() + " : " + p.getTotal6sScored());
                System.out.println("Total Balls played by " + p.getName() + " : " + p.getTotalBallPlayed());
                System.out.println("***********************************************************************");
            }
        }
    }
    public void saveTeamStatus(Team winner,Team loser,boolean isDraw){
        if(isDraw==true){
            teamDao.updateStatus(winner.get_id(),TeamStatus.TIE.name());
            teamDao.updateStatus(loser.get_id(),TeamStatus.TIE.name());
        }
        else{
            teamDao.updateStatus(winner.get_id(),TeamStatus.WIN.name());
            teamDao.updateStatus(loser.get_id(),TeamStatus.LOSE.name());
        }
    }
}
