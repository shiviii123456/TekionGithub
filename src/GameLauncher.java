import java.util.Scanner;

public class GameLauncher {

    Scanner sc = new Scanner(System.in);

    public void start() throws InterruptedException {

        System.out.println("Enter the number of overs ");
        int over = sc.nextInt();

        sc.nextLine();
        System.out.println("Enter the name of Team1 ");
        String name1 = sc.nextLine();
        System.out.println("Enter the name of Team2 ");
        String name2 = sc.nextLine();


        Teams team1 = new Teams();
        team1.setName(name1);

        Teams team2 = new Teams();
        team2.setName(name2);

        MatchController match = new MatchController();
        Teams batting = match.toss(team1, team2);
        System.out.println(batting.getName() + " decided to bat first ");


        match.gameLogic(batting, over, 0);
        Teams bowling;
        if (batting == team1) {
            bowling = team2;
        } else {
            bowling = team1;
        }


        match.gameLogic(bowling, over, batting.getScore() + 1);

        ScoreBoard.scoreBoard(team1, team2);
    }

}
