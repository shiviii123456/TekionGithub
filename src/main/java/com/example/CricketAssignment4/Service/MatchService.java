package com.example.CricketAssignment4.Service;

import com.example.CricketAssignment4.Enum.TeamStatus;
import com.example.CricketAssignment4.Model.Match;
import com.example.CricketAssignment4.Model.ScoreBoard;
import com.example.CricketAssignment4.Model.Team;
import com.example.CricketAssignment4.Repository.MatchRepo;
import com.example.CricketAssignment4.Repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class MatchService {
    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private TeamRepo teamRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ScoreBoardService scoreBoardService;
    Scanner sc = new Scanner(System.in);

    public ResponseEntity<Object> addMatch(Match matchData) {
        String team1Id = matchData.getTeam1Id();
        String team2Id = matchData.getTeam2Id();
        int over = matchData.getOver();
        if (team1Id == null || team2Id == null || team1Id.isEmpty() || team2Id.isEmpty() || over == 0)
            return ResponseEntity.badRequest().body("Team1Id, Team2Id & over cannot be null");
        if (team1Id.equals(team2Id)) return ResponseEntity.badRequest().body("Team1Id and team2Id cannot be same");
        Optional<Team> team1Data = teamRepo.findById(team1Id);
        Optional<Team> team2Data = teamRepo.findById(team2Id);
        if (!team1Data.isPresent() || !team2Data.isPresent())
            return ResponseEntity.badRequest().body("Team1Id or team2Id are not valid");
        List<String> team1 = team1Data.get().getPlayers();
        List<String> team2 = team2Data.get().getPlayers();
        if (team1 == null || team2 == null || team1.size() != team2.size())
            return ResponseEntity.badRequest().body("team1 and team2 size should be same");

        try {
            Match match = matchRepo.save(matchData);
            return ResponseEntity.ok(match);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Match cannot be created");
        }
    }

    public ResponseEntity<Object> allMatch() {
        List<Match> allMatch = matchRepo.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Details of all Matches fetched successfully");
        response.put("Match", allMatch);
        return ResponseEntity.ok(allMatch.size() == 0 ? "No matches found" : response);
    }

    public ResponseEntity<Object> getMatchById(String id) {
        try {
            Map<String, Object> response = new HashMap<>();
            Optional<Match> match=matchRepo.findById(id);
            if(!match.isPresent()) return ResponseEntity.badRequest().body("matchId is not correct");
            response.put("message", "Details Match fetched successfully");
            response.put("Match", match);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Match cannot be fetched");
        }
    }

    public ResponseEntity<Object> startMatch(Match match) {
        Team team1 = teamRepo.findById(match.getTeam1Id()).get();
        Team team2 = teamRepo.findById(match.getTeam2Id()).get();
        Team batting = toss(team1, team2);
        Team bowling;
        if (batting == team1) bowling = team2;
        else bowling = team1;

        match.setBattingTeamId(batting.getTeamId());
        match.setBowlingTeamId(bowling.getTeamId());
        int target = matchSimulator(match, batting, match.getOver(), 0);
        matchSimulator(match, bowling, match.getOver(), target + 1);
        matchRepo.save(match);
        scoreBoardService.matchResult(match, match.getScoreboard1Id(), match.getScoreboard2Id());
        matchRepo.save(match);
        return ResponseEntity.ok("Match Started");
    }

    public Team toss(Team team1, Team team2) {
        int toss = (int) (Math.random() * 2);
        int choice[] = {1, 2};
        int decision = choice[(int) (Math.random() * 2)];
        return ((toss == 0 && decision == 1) || (toss == 1 && decision == 2)) ? team1 : team2;
    }

    public int matchSimulator(Match match, Team team, int overSize, int target) {
        int total4s = 0;
        int total6s = 0;
        int ballPlayed = 0;
        int totalScore = 0;
        int wickets = 0;
        int teamScore = 0;
        int size = team.getPlayers().size();  // size of the team
        List<String> teamPlayer = team.getPlayers(); // Fetching the playerIds
        System.out.println(teamPlayer);
        List<List<Integer>> scorePerOver = new ArrayList<>();
        String p = teamPlayer.get(wickets);
        System.out.println(p);
        for (int over = 0; over < overSize && wickets < size - 1; over++) {
            System.out.println(over + " over ");
            ArrayList<Integer> runPerOver = new ArrayList<>();
            for (int ball = 0; ball < 6 && wickets < size - 1; ball++) {
                int runPerBall = playerService.runsMadeByPlayer(p);
                System.out.println(runPerBall + "run per Ball");
                ballPlayed++;
                runPerOver.add(runPerBall);
                if (runPerBall == 0) {
                    wickets++;
                    System.out.println(p + "playerId" + total4s + "total4s" + total6s + "total6s" + ballPlayed + "ballPlayed" + totalScore + "totalScore");
                    playerService.updateScore(p, total4s, total6s, ballPlayed, totalScore);
                    total4s = 0;
                    total6s = 0;
                    ballPlayed = 0;
                    totalScore = 0;
                    p = teamPlayer.get(wickets);
                } else {
                    if (runPerBall == 4) total4s++;
                    else if (runPerBall == 6) total6s++;
                    totalScore += runPerBall;
                    teamScore += runPerBall;
                }
                if (match.getBowlingTeamId().equals(team.getTeamId()) && teamScore > target) break;
            }
            System.out.println(p + "playerId" + total4s + "total4s" + total6s + "total6s" + ballPlayed + "ballPlayed" + totalScore + "totalScore");
            playerService.updateScore(p, total4s, total6s, ballPlayed, totalScore);
            scorePerOver.add(runPerOver);
            if (match.getBowlingTeamId().equals(team.getTeamId()) && teamScore > target) break;
        }
        scoreBoardService.updateScoreBoard(match, scorePerOver, wickets, teamScore, team.getTeamId());
        return teamScore;
    }

}
