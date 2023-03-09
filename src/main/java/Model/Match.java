package Model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    private String _id;
    private String team1Id;
    private String team2Id;
    private int over;

}
