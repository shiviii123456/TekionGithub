package Model;
import Enum.PlayerRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String _id;
    private String name;
    private String teamId;
    private String playerRole;
    private int totalBallPlayed, total4sScored, total6sScored;
    private int totalBattingScore;

}
