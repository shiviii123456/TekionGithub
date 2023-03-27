package com.example.CricketAssignment4.Controller;

import com.example.CricketAssignment4.Service.ScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scoreBoard")
public class ScoreBoardController {
    @Autowired
    private ScoreBoardService scoreBoardService;
    @GetMapping("/get/{scoreboardId}")
    public ResponseEntity<Object> getScoreBoard(@PathVariable String scoreboardId){
        return scoreBoardService.getScoreBoard(scoreboardId);
    }
}
