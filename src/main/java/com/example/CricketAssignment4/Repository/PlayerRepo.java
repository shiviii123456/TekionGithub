package com.example.CricketAssignment4.Repository;

import com.example.CricketAssignment4.Model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepo extends MongoRepository<Player,String> {
}
