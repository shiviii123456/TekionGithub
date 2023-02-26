public class ScoreBoard {

    public static void overScore(Teams team1, Teams team2, int[] runPerBall, int over) {
        System.out.println("---------------------------------Score Of " + team1.getName() + " in " + over + " over " + "-------------------------------------------");
        int teamScore = 0;
        for (int run : runPerBall) {
            if (run > 0) {
                System.out.print(run + " ");
                teamScore += run;
            } else if (run == -1)
                System.out.print("W" + " ");
        }
        System.out.println();
        for (Player p : team1.getPlayers()) {
            if (p.getTotalBattingScore() > 0) {
                System.out.println("Runs made by " + p.getName() + " : " + p.getTotalBattingScore());
                System.out.println("Total 4s made by " + p.getName() + " : " + p.getTotal4sScored());
                System.out.println("Total 6s made by " + p.getName() + " : " + p.getTotal6sScored());
                System.out.println("Total Balls played by " + p.getName() + " : " + p.getTotalBallPlayed());
                System.out.println("--------------------------------------------------------------");
            }
        }
        team1.setScore(team1.getScore() + teamScore);
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
