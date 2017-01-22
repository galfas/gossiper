package com.mjs.gossiper.dao.impl;

import com.google.gson.Gson;
import com.mjs.gossiper.dao.AccountRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Component
public class AccountRepositoryImpl implements AccountRepository {

    public static final String COLLECTION_NAME = "player";

    @Value("${database.mongodb.name}")
    private String databaseName;

    public Account insert(Account account) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.insertOne(Document.parse(new Gson().toJson(account)));

        return account;
    }

    @Override
    public Account getAccountFor(BasicAccount basicAccount) {
        Account account = null;

        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document accountAsDocument = collection.find(eq("name", basicAccount.getName())).first();

        if (accountAsDocument != null) {
            account = new Gson().fromJson(accountAsDocument.toJson(), Account.class);
        }

        return account;
    }
}
