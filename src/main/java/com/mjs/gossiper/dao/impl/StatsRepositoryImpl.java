package com.mjs.gossiper.dao.impl;

import com.google.gson.Gson;
import com.mjs.gossiper.dao.StatsRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.Feeds;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Component
public class StatsRepositoryImpl implements StatsRepository {

    public static final String COLLECTION_NAME = "numbers";

    @Value("${database.mongodb.name}")
    private String databaseName;


    @Override
    public Feeds insert(Feeds feeds){
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.insertOne(Document.parse(new Gson().toJson(feeds)));

        return feeds;
    }

    @Override
    public Feeds getStats(Account account){
        Feeds feeds = null;

        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document accountAsDocument = collection.find(eq("summonerId", account.getId())).first();

        if (accountAsDocument != null) {
            feeds = new Gson().fromJson(accountAsDocument.toJson(), Feeds.class);
        }

        return feeds;
    }

}
