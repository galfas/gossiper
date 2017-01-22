package com.mjs.gossiper.dao.impl;

import com.google.gson.Gson;
import com.mjs.gossiper.dao.StatsRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.PlayerStat;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
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
    public PlayerStat insert(PlayerStat playerStat) {
        MongoCollection<Document> collection = mongoDatabaseClient.getCollection(COLLECTION_NAME);

        collection.updateOne(eq(SUMMONER_ID_FIELD, playerStat.getSummonerId()), new Document("$set",
                Document.parse(new Gson().toJson(playerStat))),
                (new UpdateOptions()).upsert(true));

        return playerStat;
    }

    @Override
    public PlayerStat getStats(Account account) {
        PlayerStat playerStat = null;

        MongoCollection<Document> collection = mongoDatabaseClient.getCollection(COLLECTION_NAME);

        Document accountAsDocument = collection.find(eq(SUMMONER_ID_FIELD, account.getId())).first();

        if (accountAsDocument != null) {
            playerStat = new Gson().fromJson(accountAsDocument.toJson(), PlayerStat.class);
        }

        return playerStat;
    }
}
