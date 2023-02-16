import java.util.Scanner;

public class MatchController {
    Scanner sc = new Scanner(System.in);

    public Teams toss(Teams team1, Teams team2) {
        int toss = (int) (Math.random() * 2);
        System.out.println(toss == 0 ? team1.getName() + " has won the toss " : team2.getName() + " has won the toss ");
        if (toss == 0) {
            System.out.println("Enter 1 to do the batting and 2 for bowling :");
            int decision = sc.nextInt();
            if (decision == 1) return team1;
            return team2;
        } else {
            System.out.println("Enter 1 to do the batting and 2 for bowling :");
            int decision = sc.nextInt();
            if (decision == 1) return team2;
            return team1;
        }

    }
   //wickets Thread target
    public void gameLogic(Teams batting, int over,int target) throws InterruptedException {
        System.out.println(batting.getName() + " scores : ");
        int score=0;
        int wickets=0;
        for (int i = 0; i < over && wickets<=10; i++) {
            System.out.println("In over " + (i + 1));
            for (int j = 0; j < 6 && wickets<=10; j++) {
                batting.over[j] = (int) (Math.random() * 8);
                if (batting.over[j] == 7) {
                    wickets++;
                    batting.setWickets(wickets);
                    System.out.print("W" + " ");
                    continue;
                }
                System.out.print(batting.over[j] + " ");
                score += batting.over[j];
                batting.setScore(score);
                if(batting.getInnings()==2 && score>target){
                    System.out.println();
                    return;
                }
                Thread.sleep(1000);
            }
            System.out.println();
            Thread.sleep(2000);
        }

    }
}
