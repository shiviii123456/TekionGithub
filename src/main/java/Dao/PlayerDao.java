package Dao;

import Connection.DatabaseConnection;
import Model.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import Enum.PlayerRole;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class PlayerDao {
    public static MongoCollection<Document> playerCollection;
    public static ObjectMapper objectMapper;
    public PlayerDao() {
        MongoDatabase db = DatabaseConnection.getDatabase();
        playerCollection = db.getCollection("Player");
        objectMapper=new ObjectMapper();
    }
    public String addPlayer(String name, PlayerRole playerRole,String teamId) {
        UUID uuid = UUID.randomUUID();
        String playerId = uuid.toString();
        Document player = new Document("_id", playerId);
        player.append("name", name)
                .append("teamId", teamId)
                .append("playerRole", playerRole.name())
                .append("totalBallPlayed", 0)
                .append("total4sScored", 0)
                .append("total6sScored", 0)
                .append("totalBattingScore",0);
        playerCollection.insertOne(player);
        return playerId;
    }
    public void updateScore(String id,int total4s,int total6s,int ballPlayed,int totalScore){
        Bson filter=eq("_id",id);
        playerCollection.updateOne(filter,
                Updates.combine(
                        Updates.set("total4sScored",total4s),
                        Updates.set("total6sScored",total6s),
                        Updates.set("totalBattingScore",totalScore),
                        Updates.set("totalBallPlayed",ballPlayed)));

    }

    public Player getPlayer(String id) {
        Bson filter=eq("_id",id);
        Document document=playerCollection.find(filter).first();
        Player player = null;
        try {
            player = objectMapper.readValue(document.toJson(),Player.class);
        } catch (Exception e) {
            System.out.println("ERROR !");
        }
       return player;

    }
}
