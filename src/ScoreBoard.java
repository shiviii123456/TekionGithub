import java.util.ArrayList;

public class ScoreBoard {
    public static void teamScore(Teams team) {
        System.out.println("---------------------------------Score Of " + team.getName() + "-------------------------------------------------");
        int c = 1;
        for (ArrayList<Integer> over : team.getScorePerOver()) {
            System.out.println("In Over : " + c);
            for (int run : over) {
                if (run == 7)
                    System.out.print("W ");
                else
                    System.out.print(run + " ");
            }
            c++;
            System.out.println();
            System.out.println();
        }
        for (Player p : team.getPlayers()) {
            if (p.getTotalBattingScore() > 0 || p.getTotalBallPlayed()>0) {
                System.out.println("Runs made by " + p.getName() + " : " + p.getTotalBattingScore());
                System.out.println("Total 4s made by " + p.getName() + " : " + p.getTotal4sScored());
                System.out.println("Total 6s made by " + p.getName() + " : " + p.getTotal6sScored());
                System.out.println("Total Balls played by " + p.getName() + " : " + p.getTotalBallPlayed());
                System.out.println("--------------------------------------------------------------");
            }
        }
    }

    public static void scoreBoard(Teams team1, Teams team2) {

        System.out.println("--------------------------------------------------Match Results---------------------------------------------------");
        System.out.println("Total score of " + team1.getName() + " " + team1.getScore() + " runs at wickets " + team1.getWickets() + "/10");
        System.out.println("Total score of " + team2.getName() + " " + team2.getScore() + " runs at wickets " + team2.getWickets() + "/10");
        if (team1.getScore() > team2.getScore()) {
            System.out.println(team1.getName() + " has won the match by " + (team1.getScore() - team2.getScore()) + " runs ");
        } else if (team1.getScore() == team2.getScore()) {
            System.out.println("Tie between " + team1.getName() + " and " + team2.getName());
        } else {
            System.out.println(team2.getName() + " has won the match by " + (team2.getScore() - team1.getScore()) + " runs ");
        }
    }
}
