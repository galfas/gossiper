package com.mjs.gossiper.business.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mjs.gossiper.domain.Account;
import feign.gson.GsonDecoder;

import java.util.Map;
import java.util.Set;

/**
 * Created by marcelosaciloto on 21/01/2017.
 */
public class Test {

    @org.junit.Test
    public void shouldTest() {
        String test = "{\"Account\":{\"id\":64173760,\"name\":\"Galfas\",\"profileIconId\":27,\"revisionDate\":1417817810000,\"summonerLevel\":3}}";
        Gson gson = new Gson();
        JsonObject account = gson.fromJson(test, JsonObject.class);
        Account account1 = jsonToAccount(account);
        System.out.println(account1.getName());
    }

    private Account jsonToAccount(JsonObject accountAsJsonObject) {
        Account account = null;
        Set<Map.Entry<String, JsonElement>> entries = accountAsJsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            account = new Gson().fromJson(entry.getValue(), Account.class);
        }

        return account;
    }
}
