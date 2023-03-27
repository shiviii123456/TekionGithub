package com.example.CricketAssignment4.Service;

import com.example.CricketAssignment4.Enum.PlayerRole;
import com.example.CricketAssignment4.Model.Player;
import com.example.CricketAssignment4.Repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

    public ResponseEntity<Object> addPlayer(Player player) {
        if (player.getPlayerName() == null || player.getPlayerRole() == null || player.getPlayerName().isEmpty() || player.getPlayerRole().toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Player name and Player role cannot be null or Empty");
        }
        try {
            Map<String, Object> response = new HashMap<>();
            Player playerData = playerRepo.save(player);
            response.put("message", "Player created successfully");
            response.put("player", playerData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create player");
        }
    }

    public ResponseEntity<Object> getPlayerById(String playerId) {
        try {
            Optional<Player> player = playerRepo.findById(playerId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Player fetched successfully");
            response.put("Player", player.get());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("player not found with " + playerId + " id ");
        }
    }

    public ResponseEntity<Object> updatePlayerData(String playerId, Player playerData) {
        if (playerData.getPlayerName() == null || playerData.getPlayerRole() == null || playerData.getPlayerName().isEmpty() || playerData.getPlayerRole().toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Player name and Player role cannot be null or Empty");
        }
        try {
           Player p=playerRepo.findById(playerId)
                    .map(player -> {
                        player.setPlayerName(playerData.getPlayerName());
                        player.setPlayerRole(playerData.getPlayerRole());
                        return playerRepo.save(player);
                    }).get();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Player data updated");
            response.put("Player", p);
           return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Player with " + playerId + " cannot be updated ");
        }

    }
    public ResponseEntity<Object> getAllPlayer() {
        Map<String, Object> response = new HashMap<>();
        List<Player> allPlayer = playerRepo.findAll();
        response.put("message", "List of all Players");
        response.put("player", allPlayer);
        return ResponseEntity.ok(allPlayer.size() == 0 ? "No Player Found" : response);
    }

    public int runsMadeByPlayer(String playerId) {
        Random r = new Random();
        Player player = playerRepo.findById(playerId).get();
        int probability = r.nextInt(100);
        int run = r.nextInt(7);
        System.out.println();
        if (player.getPlayerRole().equals(PlayerRole.BATSMAN.name())) {
            return probability < 60 ? 6 : run;
        } else {
            return probability < 60 ? 2 : run;
        }
    }

    public void updateScore(String id, int total4s, int total6s, int ballPlayed, int totalScore) {
        playerRepo.findById(id).map(player -> {
            player.setTotalBattingScore(totalScore + player.getTotalBattingScore());
            player.setTotal4sScored(total4s + player.getTotal4sScored());
            player.setTotal6sScored(total6s + player.getTotal6sScored());
            player.setTotalBallPlayed(ballPlayed + player.getTotalBallPlayed());
            return playerRepo.save(player);
        });
    }
}
