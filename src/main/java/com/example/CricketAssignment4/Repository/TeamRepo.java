package com.example.CricketAssignment4.Repository;

import com.example.CricketAssignment4.Model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepo extends MongoRepository<Team,String> {
}
