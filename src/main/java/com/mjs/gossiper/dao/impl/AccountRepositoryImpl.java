package com.mjs.gossiper.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mjs.gossiper.dao.AccountRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.util.GsonCustomizedTypeAdapterFactory;
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

    @Value("${database.mongodb.name}")
    private String databaseName;

    private static final String COLLECTION_NAME = "player";

    private Gson gsonWithTypeAdapter = new GsonBuilder().registerTypeAdapterFactory(new AccountTypeAdapterGson()).create();


    public Account insert(Account account) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.insertOne(Document.parse(gsonWithTypeAdapter.toJson(account)));

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
            account = gsonWithTypeAdapter.fromJson(accountAsDocument.toJson(), Account.class);
        }

        return account;
    }


    class AccountTypeAdapterGson extends GsonCustomizedTypeAdapterFactory<Account> {
        public AccountTypeAdapterGson() {
            super(Account.class);
        }

        @Override
        protected void afterRead(JsonElement deserialized) {
            updateLongObject(deserialized, "revisionDate");
            updateLongObject(deserialized, "lastUpdate");
        }

        private void updateLongObject(JsonElement deserialized, String field) {
            JsonObject longField = deserialized.getAsJsonObject().get(field).getAsJsonObject();
            deserialized.getAsJsonObject().remove(field);
            deserialized.getAsJsonObject().add(field, longField.get(field));
        }
    }
}