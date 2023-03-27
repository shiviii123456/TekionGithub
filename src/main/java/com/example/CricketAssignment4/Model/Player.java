package com.example.CricketAssignment4.Model;

import com.example.CricketAssignment4.Enum.PlayerRole;
import com.example.CricketAssignment4.Repository.PlayerRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Player {
    @Id
    private String playerId;
    private String playerName;
    private PlayerRole playerRole;
    private int totalBallPlayed, total4sScored, total6sScored;
    private int totalBattingScore;
}
