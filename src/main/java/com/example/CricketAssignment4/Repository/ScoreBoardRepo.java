package com.example.CricketAssignment4.Repository;

import com.example.CricketAssignment4.Model.ScoreBoard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreBoardRepo extends MongoRepository<ScoreBoard,String> {

}
