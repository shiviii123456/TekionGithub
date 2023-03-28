package com.example.CricketAssignment4.Controller;

import com.example.CricketAssignment4.Model.Match;
import com.example.CricketAssignment4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/startMatch")
    public  ResponseEntity<Object> startMatch(@RequestBody Match match){
         return   matchService.startMatch(match);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addMatch(@RequestBody Match matchData){
            return matchService.addMatch(matchData);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> allMatch(){
           return matchService.allMatch();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getMatchById(@PathVariable String id){
            return matchService.getMatchById(id);
    }


}
