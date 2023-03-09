package Connection;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;

public class DatabaseConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static String url = "mongodb://localhost:27017";
    private static String databaseName = "CricketGame";

    public static MongoDatabase getDatabase() throws MongoException {
        if (database == null) {
            mongoClient = MongoClients.create(url);
            database = mongoClient.getDatabase(databaseName);
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}
