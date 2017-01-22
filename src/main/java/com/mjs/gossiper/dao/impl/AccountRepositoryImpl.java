package com.mjs.gossiper.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mjs.gossiper.dao.AccountRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.util.GsonCustomizedTypeAdapterFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Component
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private MongoDatabase mongoDatabaseClient;

    private Gson gsonWithTypeAdapter = new GsonBuilder()
            .registerTypeAdapterFactory(new AccountTypeAdapterGson())
            .create();

    private static final String COLLECTION_NAME = "player";
    private static final String NAME_FIELD = "name";
    private static final String REVISION_DATE_FIELD = "revisionDate";
    private static final String LAST_UPDATE_FIELD = "lastUpdate";


    @Override
    public Account insert(Account account) {
        MongoCollection<Document> collection = mongoDatabaseClient.getCollection(COLLECTION_NAME);

        collection.updateOne(eq(NAME_FIELD, account.getName()), new Document("$set",
                                Document.parse(gsonWithTypeAdapter.toJson(account))),
                                (new UpdateOptions()).upsert(true));

        return account;
    }

    @Override
    public Account getAccountFor(BasicAccount basicAccount) {
        Account account = null;

        MongoCollection<Document> collection = mongoDatabaseClient.getCollection(COLLECTION_NAME);

        Document accountAsDocument = collection.find(eq(NAME_FIELD, basicAccount.getName())).first();

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
            updateLongObject(deserialized, REVISION_DATE_FIELD);
            updateLongObject(deserialized, LAST_UPDATE_FIELD);
        }

        private void updateLongObject(JsonElement deserialized, String field) {
            JsonObject longField = deserialized.getAsJsonObject().get(field).getAsJsonObject();
            deserialized.getAsJsonObject().remove(field);
            deserialized.getAsJsonObject().add(field, longField.get("$numberLong"));
        }
    }
}