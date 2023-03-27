package com.example.CricketAssignment4.Repository;

import com.example.CricketAssignment4.Model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepo extends MongoRepository<Match,String> {
}
