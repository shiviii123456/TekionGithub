import java.util.ArrayList;
import java.util.Scanner;

public class MatchController {
    int totalScore;
    int ballPlayed;
    int total4s;
    int total6s;
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

    public void matching(Teams team1, Teams team2, int overSize, int target) throws InterruptedException {
        total4s = 0;
        total6s = 0;
        ballPlayed = 0;
        totalScore = 0;
        int wickets = 0;
        Double run;
        int size = team1.getPlayers().size();  // size of the team
        ArrayList<Player> team1Player = team1.getPlayers();

        for (int over = 0; over < overSize && wickets < size - 1; over++) {
            Player p = null;
            int[] runPerBall = new int[6];
            for (int ball = 0; ball < 6 && wickets < size - 1; ball++) {
                p = team1Player.get(wickets);
                run = Math.random();
                int currScore = totalScore;
                if (playerOut(p, run, runPerBall)) {
                    wickets++;
                    p.setTotal4sScored(total4s);
                    p.setTotal6sScored(total6s);
                    p.setTotalBattingScore(totalScore);
                    p.setTotalBallPlayed(ballPlayed);
                    runPerBall[ball] = -1;
                    total4s = 0;
                    total6s = 0;
                    ballPlayed = 0;
                    totalScore = 0;
                    p = team1Player.get(wickets);
                } else {
                    runPerBall[ball] = totalScore - currScore;
                }
            }
            if (p != null) {
                p.setTotal4sScored(total4s);
                p.setTotal6sScored(total6s);
                p.setTotalBattingScore(totalScore);
                p.setTotalBallPlayed(ballPlayed);
            }
            ScoreBoard.overScore(team1, team2, runPerBall, over + 1);
        }
        team1.setWickets(wickets);
    }

    public boolean runsMadeByPlayer(Player p, Double run, int[] runPerBall) {
        ballPlayed++;
        if (p.getPlayerRole().equals("BatsMan")) {
            if (run >= 0.0 && run <= 0.1) {
                totalScore += 1;
            } else if (run > 0.1 && run <= 0.2) {
                totalScore += 2;
            } else if (run > 0.2 && run <= 0.4) {
                totalScore += 3;
            } else if (run > 0.4 && run <= 0.5) {
                return true;
            } else if (run > 0.5 && run <= 0.8) {
                total4s++;
                totalScore += 4;
            } else if (run > 0.8 && run <= 0.9) {
                total6s++;
                totalScore += 6;
            } else {
                totalScore += 5;
            }
        } else {
            if (run >= 0.0 && run <= 0.1) {
                totalScore += 1;
            } else if (run > 0.1 && run <= 0.3) {
                totalScore += 2;
            } else if (run > 0.3 && run <= 0.45) {
                totalScore += 3;
            } else if (run > 0.45 && run <= 0.5) {
                totalScore += 4;
            } else if (run > 0.5 && run <= 0.85) {
                return true;
            } else if (run > 0.85 && run <= 0.9) {
                totalScore += 6;
            } else {
                totalScore += 5;
            }
        }
        return false;
    }
}
