package com.example.CricketAssignment4.Controller;

import com.example.CricketAssignment4.Model.Player;
import com.example.CricketAssignment4.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/add")
    public ResponseEntity<Object> addPlayer(@RequestBody Player player) {
            return playerService.addPlayer(player);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllPlayers(){
            return playerService.getAllPlayer();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getPlayerById(@PathVariable String id) {
            return playerService.getPlayerById(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePlayerData(@PathVariable String id,@RequestBody Player playerData) {
           return playerService.updatePlayerData(id,playerData);
    }
}
