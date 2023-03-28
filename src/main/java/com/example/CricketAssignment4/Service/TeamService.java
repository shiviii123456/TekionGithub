package com.example.CricketAssignment4.Service;

import java.util.*;

import com.example.CricketAssignment4.Model.Player;
import com.example.CricketAssignment4.Model.Team;
import com.example.CricketAssignment4.Repository.PlayerRepo;
import com.example.CricketAssignment4.Repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepo teamRepo;
    @Autowired
    private PlayerRepo playerRepo;

    public ResponseEntity<Object> addTeam(Team teamData) {
        if (teamData.getTeamName() == null || teamData.getTeamName().isEmpty())
            return ResponseEntity.badRequest().body("Team Name cannot be null");
        try {
            Team team = teamRepo.save(teamData);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Team created successfully");
            response.put("team", team);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Team cannot be created");
        }
    }

    public ResponseEntity<Object> updateTeamPlayers(String teamId, String playerId) {
        if (teamId == null || playerId == null || teamId.isEmpty() || playerId.isEmpty())
            return ResponseEntity.badRequest().body("TeamId and PlayerId cannot be null and Empty");
        try {
            Optional<Player> playerData=playerRepo.findById(playerId);
            if (!playerData.isPresent())
                return ResponseEntity.badRequest().body("Player with Id is not found");
            Player player=playerData.get();
            if(player.getTeamId()!=null)
                return ResponseEntity.badRequest().body("Player is already added to the another team");
            Optional<Team> teamData = teamRepo.findById(teamId);
            if (!teamData.isPresent()) return ResponseEntity.badRequest().body("Team with Id is not found");
            Team team=teamData.get();
            List<String> teamPlayer;
            if (team.getPlayers() == null) teamPlayer = new ArrayList<>();
            else {
                if (isPlayerPresent(playerId, team.getPlayers()))
                   return ResponseEntity.badRequest().body("Player Id already present");
                teamPlayer = team.getPlayers();
            }
            teamPlayer.add(playerId);
            team.setPlayers(teamPlayer);
            player.setTeamId(teamId);
            playerRepo.save(player);
            teamRepo.save(team);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Team players cannot be updated");
        }
    }
    public ResponseEntity<Object> getAllTeam() {
        Map<String, Object> response = new HashMap<>();
        List<Team> team=teamRepo.findAll();
        response.put("message", "List of all Teams");
        response.put("Teams", team);
        return ResponseEntity.ok(team.size()==0?"No Teams Found":response);
    }
    public ResponseEntity<Object> getTeamById(String teamId) {
        if (teamId == null || teamId.isEmpty()) return ResponseEntity.badRequest().body("TeamId cannot be null and Empty");
        try {
            Optional<Team> team = teamRepo.findById(teamId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Team fetched successfully");
            response.put("Team", team.get());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Team Id does not exist");
        }
    }

    public boolean isPlayerPresent(String playerId, List<String> player) {
        for (String id : player)
            if (id.equals(playerId)) return true;
        return false;
    }
}
