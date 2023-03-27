package com.example.CricketAssignment4.Controller;

import com.example.CricketAssignment4.Model.Player;
import com.example.CricketAssignment4.Model.Team;
import com.example.CricketAssignment4.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/add")
    public ResponseEntity<Object> addTeam(@RequestBody Team teamData){
           return teamService.addTeam(teamData);
    }
    @PutMapping("/updatePlayers/{teamId}/{playerId}")
    public ResponseEntity<Object> updateTeamPlayers(@PathVariable String teamId,@PathVariable String playerId){
            return teamService.updateTeamPlayers(teamId,playerId);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllTeams(){
            return teamService.getAllTeam();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getTeamById(@PathVariable String id){
            return teamService.getTeamById(id);
    }
}
