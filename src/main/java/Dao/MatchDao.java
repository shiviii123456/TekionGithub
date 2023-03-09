package Dao;

import Connection.DatabaseConnection;
import Model.Match;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;


import javax.xml.crypto.Data;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MatchDao {
    public static MongoCollection<Document> matchCollection;
    public static ObjectMapper objectMapper;

    public MatchDao() {
        MongoDatabase db = DatabaseConnection.getDatabase();
        matchCollection = db.getCollection("Match");
        objectMapper = new ObjectMapper();
    }

    public String addMatch(String team1Id, String team2Id, int over) {
        UUID uuid = UUID.randomUUID();
        String matchId = uuid.toString();
        Document document = new Document("_id", matchId);
        document.append("team1Id", team1Id)
                .append("team2Id", team2Id)
                .append("over", over);
        matchCollection.insertOne(document);
        return matchId;
    }

    public Match findMatchById(String matchId) {
        Bson filter = eq("_id", matchId);
        Document document = matchCollection.find(filter).first();
        Match match = null;
        try {
            match = objectMapper.readValue(document.toJson(), Match.class);
        } catch (Exception e) {
            System.out.println("Error !");
        }
        return match;
    }
}
