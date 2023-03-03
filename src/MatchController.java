import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
public class MatchController {
    Scanner sc = new Scanner(System.in);

    public Team toss(Team team1, Team team2) {
        int toss = (int) (Math.random() * 2);
        System.out.println(toss == 0 ? team1.getName() + " has won the toss " : team2.getName() + " has won the toss ");
        System.out.println("Enter 1 to do the batting and 2 for bowling :");
        int decision = sc.nextInt();
        String result = (decision == 1) ? "batting" : "bowling";
        System.out.println((toss==0?(team1.getName()):(team2.getName()))+" decided to do "+result);
        return ((toss==0 && decision==1) ||(toss==1 && decision==0)) ? team1:team2;
    }

    public void matchSimulator(Team team1, Team team2, int overSize, int target) {
        int total4s = 0;
        int total6s = 0;
        int ballPlayed = 0;
        int totalScore = 0;
        int wickets = 0;
        double run;
        int teamScore=0;

        int size = team1.getPlayers().size();  // size of the team
        List<Player> team1Player = team1.getPlayers();
        List<List<Integer>> scorePerOver=new ArrayList<>();
        Player p = team1Player.get(wickets);

        for (int over = 0; over < overSize && wickets < size - 1; over++) {
            ArrayList<Integer> runPerOver=new ArrayList<>();
            for (int ball = 0; ball < 6 && wickets < size - 1; ball++) {
                run = Math.random();
                int runPerBall = runsMadeByPlayer(p, run);
                ballPlayed++;
                runPerOver.add(runPerBall);
                if (runPerBall == 7) {
                    wickets++;
                    p.updateScore(total4s,total6s,ballPlayed,totalScore);
                    total4s = 0;
                    total6s = 0;
                    ballPlayed = 0;
                    totalScore = 0;
                    p = team1Player.get(wickets);
                } else {
                    if (runPerBall == 4) total4s++;
                    else if (runPerBall == 6) total6s++;
                    totalScore += runPerBall;
                    teamScore+=runPerBall;
                }
            }
            p.updateScore(total4s,total6s,ballPlayed,totalScore);
            scorePerOver.add(runPerOver);
        }
        team1.setScorePerOver(scorePerOver);
        team1.setWickets(wickets);
        team1.setScore(teamScore);
    }
    public int runsMadeByPlayer(Player p, Double run) {
        if (p.getPlayerRole()==PlayerRole.BATSMAN) {
            if (run >= 0.0 && run <= 0.1) {
                return 1;
            } else if (run > 0.1 && run <= 0.2) {
                return 2;
            } else if (run > 0.2 && run <= 0.4) {
                return 3;
            } else if (run > 0.4 && run <= 0.5) {
                return 7;
            } else if (run > 0.5 && run <= 0.8) {
                return 4;
            } else if (run > 0.8 && run <= 0.9) {
                return 6;
            } else {
                return 5;
            }
        } else {
            if (run >= 0.0 && run <= 0.1) {
                return 1;
            } else if (run > 0.1 && run <= 0.3) {
                return 2;
            } else if (run > 0.3 && run <= 0.45) {
                return 3;
            } else if (run > 0.45 && run <= 0.5) {
                return 4;
            } else if (run > 0.5 && run <= 0.85) {
                return 7;
            } else if (run > 0.85 && run <= 0.9) {
                return 6;
            } else {
                return 5;
            }
        }
    }
}
