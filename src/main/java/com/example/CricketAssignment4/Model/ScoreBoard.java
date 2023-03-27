package com.example.CricketAssignment4.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ScoreBoard {
    @Id
    private String id;
    private String teamId;
    private List<List<Integer>> scorePerBall;
    private int totalScore;
    private int wicketsTaken;

}
