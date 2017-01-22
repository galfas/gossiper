package com.mjs.gossiper.dao.impl;

import com.google.gson.Gson;
import com.mjs.gossiper.dao.StatsRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.Feeds;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Component
public class StatsRepositoryImpl implements StatsRepository {

    @Value("${database.mongodb.name}")
    private String databaseName;

    @Autowired
    private MongoDatabase mongoDatabaseClient;

    private static final String COLLECTION_NAME = "statistics";
    private static final String SUMMONER_ID_FIELD = "summonerId";


    @Override
    public Feeds insert(Feeds feeds){
        MongoCollection<Document> collection = mongoDatabaseClient.getCollection(COLLECTION_NAME);

        collection.insertOne(Document.parse(new Gson().toJson(feeds)));

        return feeds;
    }

    @Override
    public Feeds getStats(Account account){
        Feeds feeds = null;

        MongoCollection<Document> collection = mongoDatabaseClient.getCollection(COLLECTION_NAME);

        Document accountAsDocument = collection.find(eq(SUMMONER_ID_FIELD, account.getId())).first();

        if (accountAsDocument != null) {
            feeds = new Gson().fromJson(accountAsDocument.toJson(), Feeds.class);
        }

        return feeds;
    }
}
