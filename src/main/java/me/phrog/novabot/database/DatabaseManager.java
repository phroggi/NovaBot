package me.phrog.novabot.database;

import java.util.UUID;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import me.phrog.novabot.NovaBot;

public class DatabaseManager extends Database {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DatabaseManager(NovaBot plugin) {
        super(plugin);
    }

    @Override
    public boolean connect() {
        String connString = this.plugin.getConfig().getString("database.mongo.connection_string");
        String database = this.plugin.getConfig().getString("database.mongo.database");     

        ConnectionString connectionString = new ConnectionString(connString);
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build()
                )
            .build();    

        this.client = MongoClients.create(settings);
        this.database = this.client.getDatabase(database);
        this.collection = this.database.getCollection("novabot");

        return true;
    }

    @Override
    public void close() {
        this.client.close();
    }

    // @Override
    // public void setupTable() {
    //     try {
    //         PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS NovaBot(uuid CHAR(36) NOT NULL UNIQUE, discord_id varchar(18), PRIMARY KEY (UUID))");
    //         preparedStatement.executeUpdate();
    //     } catch(SQLException e) {
    //         e.printStackTrace();
    //     }      
    // }

    @Override
    public String getDiscordId(UUID uuid) {
        Document document = this.collection.find(Filters.eq("_id", uuid.toString())).first();
        if(document != null) return document.getString("discordId");
        return null;
    }

    @Override
    public void linkPlayer(UUID uuid, String discordId) {
       Document document = new Document()
            .append("_id", uuid.toString())
            .append(discordId, discordId);

        this.collection.insertOne(document);
    }

    @Override
    public void unlinkPlayer(UUID uuid) {
        this.collection.deleteOne(Filters.eq("_id", uuid.toString()));
    }
    
}
