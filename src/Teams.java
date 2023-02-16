public class Teams {
   private String name;
  private int score;
   int[] over=new int[6];
  private int innings;
  private int wickets;
   public void setName(String name){
       this.name=name;
   }
   public String getName(){
       return this.name;
   }

   public void setScore(int score){
       this.score=score;
   }
   public int getScore(){
       return this.score;
   }
   public void setInnings(int innings){
       this.innings=innings;
   }
   public int getInnings(){
       return innings;
   }
   public void setWickets(int wickets){
       this.wickets=wickets;
   }
   public int getWickets(){
       return wickets;
   }
}
