import java.util.ArrayList;
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
        Team team1 = new Team();
        team1.setName(name1);

        Team team2 = new Team();
        team2.setName(name2);

        Player p1 = new Player();
        p1.setName("Rohit Sharma");
        p1.setPlayerRole(PlayerRole.BATSMAN);
        Player p2 = new Player();
        p2.setName("Virat Kohli");
        p2.setPlayerRole(PlayerRole.BATSMAN);
        Player p3 = new Player();
        p3.setName("Ravindra Jadeja");
        p3.setPlayerRole(PlayerRole.BOWLER);
        Player p4 = new Player();
        p4.setName("Jasprit Bumbrah");
        p4.setPlayerRole(PlayerRole.BOWLER);

        ArrayList<Player> team1Players = new ArrayList<>();
        team1Players.add(p1);
        team1Players.add(p2);
        team1Players.add(p3);
        team1Players.add(p4);
        team1.setPlayers(team1Players);

        Player p5 = new Player();
        p5.setName("Mohammad Yousuf");
        p5.setPlayerRole(PlayerRole.BATSMAN);
        Player p6 = new Player();
        p6.setName("Shahid Afridi");
        p6.setPlayerRole(PlayerRole.BATSMAN);
        Player p7 = new Player();
        p7.setName("Shoaib Akhtar");
        p7.setPlayerRole(PlayerRole.BOWLER);
        Player p8 = new Player();
        p8.setName("Imran Khan");
        p8.setPlayerRole(PlayerRole.BOWLER);

        ArrayList<Player> team2Players = new ArrayList<>();
        team2Players.add(p5);
        team2Players.add(p6);
        team2Players.add(p7);
        team2Players.add(p8);
        team2.setPlayers(team2Players);

        MatchController match = new MatchController();
        Team batting = match.toss(team1, team2);
        Team bowling;

        if (batting == team1) {
            bowling = team2;
        } else {
            bowling = team1;
        }

        match.matchSimulator(batting, bowling, over, 0);

        match.matchSimulator(bowling, batting, over, batting.getScore() + 1);

        ScoreBoard.teamScore(team1);
        ScoreBoard.teamScore(team2);
        ScoreBoard.scoreBoard(team1,team2);
    }
}
