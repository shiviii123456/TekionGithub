package Dao;

import Connection.DatabaseConnection;
import Model.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import Model.Team;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class TeamDao {
    private static MongoCollection<Document> teamCollection;
    private static ObjectMapper objectMapper;

    public TeamDao() {
        MongoDatabase db = DatabaseConnection.getDatabase();
        teamCollection = db.getCollection("Team");
        objectMapper = new ObjectMapper();
    }

    public String addTeam(String name) {
        UUID uuid = UUID.randomUUID();
        String teamId = uuid.toString();
        Document team = new Document("_id", teamId);
        team.append("name", name)
                .append("score", 0)
                .append("wickets", 0)
                .append("teamStatus", "")
                .append("players", new ArrayList<String>())
                .append("scorePerOver", new ArrayList<>());
        teamCollection.insertOne(team);
        return teamId;
    }

    public Team getTeam(String teamId)  {
        Document document = teamCollection.find(eq("_id", teamId)).first();
         Team team=null;
         try{
             team = objectMapper.readValue(document.toJson(), Team.class);
         }
         catch (Exception e){
             System.out.println("Error !");
         }
        return team;
    }

    public void updatePlayerList(String id, List<String> playerIds) {
        Bson filter = eq("_id", id); // filter to match the document that contains the player list
        teamCollection.updateOne(filter, Updates.set("players", playerIds));
    }

    public void setTeamScore(Team team) {
        Bson filter = eq("_id", team.get_id());
        teamCollection.updateOne(filter, Updates.combine(
                Updates.set("wickets", team.getWickets())
                , Updates.set("score", team.getScore())
                , Updates.set("scorePerOver", team.getScorePerOver())));
    }

    public void updateStatus(String id, String status) {
        Bson filter = eq("_id", id);
        teamCollection.updateOne(filter, Updates.set("teamStatus", status));
    }
}
