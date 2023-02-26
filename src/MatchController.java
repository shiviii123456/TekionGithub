import java.util.ArrayList;
import java.util.Scanner;

public class MatchController {
    Scanner sc = new Scanner(System.in);

    public Teams toss(Teams team1, Teams team2) {
        int toss = (int) (Math.random() * 2);
        System.out.println(toss == 0 ? team1.getName() + " has won the toss " : team2.getName() + " has won the toss ");
        System.out.println("Enter 1 to do the batting and 2 for bowling :");
        int decision = sc.nextInt();
        if (toss == 0) {
            if (decision == 1) {
                System.out.println(team1.getName() + " decided to do batting");
                return team1;
            } else {
                System.out.println(team1.getName() + " decided to do bowling");
                return team2;
            }
        } else {
            if (decision == 1) {
                System.out.println(team2.getName() + " decided to do batting");
                return team2;
            } else {
                System.out.println(team2.getName() + " decided to do bowling");
                return team1;
            }
        }
    }

    public void matchSimulator(Teams team1, Teams team2, int overSize, int target) {
        int total4s = 0;
        int total6s = 0;
        int ballPlayed = 0;
        int totalScore = 0;
        int wickets = 0;
        Double run;
        int teamScore=0;

        int size = team1.getPlayers().size();  // size of the team
        ArrayList<Player> team1Player = team1.getPlayers();
        ArrayList<ArrayList<Integer>> scorePerOver=new ArrayList<>();
        Player p = team1Player.get(wickets);

        for (int over = 0; over < overSize && wickets < size - 1; over++) {
            ArrayList<Integer> runPerOver=new ArrayList<>();
            for (int ball = 0; ball < 6 && wickets < size - 1; ball++) {
                run = Math.random();
                int runPerBall = runsMadeByPlayer(p, run);
                ballPlayed++;
                runPerOver.add(runPerBall);
                teamScore+=runPerBall;
//               System.out.println(runPerBall+"  "+p.getName());
                if (runPerBall == 7) {
                    wickets++;
                    setPlayerData(p,total4s,total6s,ballPlayed,totalScore);
                    total4s = 0;
                    total6s = 0;
                    ballPlayed = 0;
                    totalScore = 0;
                    p = team1Player.get(wickets);
                } else {
                    if (runPerBall == 4) total4s++;
                    else if (runPerBall == 6) total6s++;
                    totalScore += runPerBall;
                }
            }
            setPlayerData(p,total4s,total6s,ballPlayed,totalScore);
            scorePerOver.add(runPerOver);
        }
        team1.setScorePerOver(scorePerOver);
        team1.setWickets(wickets);
        team1.setScore(teamScore);
    }

    public int runsMadeByPlayer(Player p, Double run) {
        if (p.getPlayerRole().equals("BatsMan")) {
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
    public void setPlayerData(Player p,int total4s,int total6s,int ballPlayed,int totalScore){
        p.setTotal4sScored(total4s);
        p.setTotal6sScored(total6s);
        p.setTotalBattingScore(totalScore);
        p.setTotalBallPlayed(ballPlayed);
    }
}
