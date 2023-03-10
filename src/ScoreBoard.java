public class ScoreBoard {
    public void scoreBoard(Teams team1,Teams team2){
        System.out.println("Total score of "+team1.getName()+" "+team1.getScore()+" at wickets "+team1.getWickets()+"/11");
        System.out.println("Total score of "+team2.getName()+" "+team2.getScore()+" at wickets "+team2.getWickets()+"/11");
        if(team1.getScore()>team2.getScore()){
            System.out.println(team1.getName()+" has won the match by "+(team1.getScore()-team2.getScore())+" runs ");
        }
        else if(team1.getScore()==team2.getScore()){
            System.out.println("Tie between "+team1.getName()+" and "+team2.getName());
        }
        else{
            System.out.println(team2.getName()+" has won the match "+(team2.getScore()-team1.getScore())+" runs ");
        }
    }
}
