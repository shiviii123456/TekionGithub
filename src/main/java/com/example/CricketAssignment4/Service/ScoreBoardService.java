package com.example.CricketAssignment4.Service;

import com.example.CricketAssignment4.Model.Match;
import com.example.CricketAssignment4.Model.ScoreBoard;
import com.example.CricketAssignment4.Model.Team;
import com.example.CricketAssignment4.Repository.ScoreBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreBoardService {
    @Autowired
    private ScoreBoardRepo scoreBoardRepo;

    public ResponseEntity<Object> getScoreBoard(String scoreboardId) {
       Optional<ScoreBoard> scoreBoard= scoreBoardRepo.findById(scoreboardId);
       try{
           return ResponseEntity.ok(scoreBoard.get());
       }catch(Exception e) {
           return ResponseEntity.badRequest().body("ScoreboardId is not valid");
       }
    }
    public void updateScoreBoard(Match match, List<List<Integer>> scorePerOver, int wickets, int teamScore, String teamId) {
        ScoreBoard scoreBoard=new ScoreBoard();
        scoreBoard.setTotalScore(teamScore);
        scoreBoard.setWicketsTaken(wickets);
        scoreBoard.setScorePerBall(scorePerOver);
        scoreBoard.setTeamId(teamId);
        ScoreBoard score=scoreBoardRepo.save(scoreBoard);
        System.out.println(score.getId()+" score of them ");
        if(teamId.equals(match.getTeam1Id())) match.setScoreboard1Id(score.getId());
        else
            match.setScoreboard2Id(score.getId());
    }

    public void matchResult(Match match,String scoreboard1Id, String scoreboard2Id) {
        ScoreBoard team1score=scoreBoardRepo.findById(scoreboard1Id).get();
        ScoreBoard team2score=scoreBoardRepo.findById(scoreboard2Id).get();
        if(team1score.getTotalScore()>team2score.getTotalScore()) match.setWinningTeamId(team1score.getTeamId());
        else if(team1score.getTotalScore()< team2score.getTotalScore()) match.setWinningTeamId(team2score.getTeamId());

    }
}
