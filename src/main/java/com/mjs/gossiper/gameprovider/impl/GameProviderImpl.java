package com.mjs.gossiper.gameprovider.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;
import com.mjs.gossiper.gameprovider.GameProvider;
import com.mjs.gossiper.gameprovider.contract.RiotProvider;
import feign.FeignException;
import feign.codec.DecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class GameProviderImpl implements GameProvider {

    private static final Logger logger = LoggerFactory.getLogger(GameProviderImpl.class);

    @Autowired
    private RiotProvider riotProvider;

    @Value("${riot.api.key}")
    private String apiRiotKey;

    @Override
    public Account fetchAccountBy(BasicAccount basicAccount) {
        logger.debug(String.format("The fetch method of data will be performed with the follow parameters '%s'", basicAccount));

        JsonObject accountAsJson = null;
        try {
            accountAsJson = riotProvider.getAccountForName(basicAccount.getName(), basicAccount.getRegion(), apiRiotKey);
        } catch (FeignException ex) {
            logger.error(String.format("Client return the error '%s' while fetching account for '%s' ", ex.getCause(), basicAccount.getName()));
            throw ex;
        } catch (Exception ex) {
            logger.error(String.format("Unexpected error '%s'", basicAccount.getName()));
            throw ex;
        }

        return jsonToAccount(accountAsJson)
                .enrichWithLocale(basicAccount)
                .enrichWithLastUpdate();
    }

    @Override
    public Feeds getStats(Account account) {
        logger.debug(String.format("It is going to fetch stats for the user that has the id '%s'", account.getId()));

        Feeds feeds = null;
        try {
            feeds = riotProvider.getStatsFor(account.getId(), account.getRegion(), apiRiotKey);
        } catch (FeignException ex) {
            logger.error(String.format("Client return the error '%s' while fetching account for '%s' ", account.getId(), ex.getCause()));
            throw ex;
        } catch (Exception ex) {
            logger.error(String.format("Unexpected error '%s'", account.getId()));
            throw ex;
        }

        return feeds;
    }

    private Account jsonToAccount(JsonObject accountAsJsonObject) {
        Account account = null;

        try {
            Set<Map.Entry<String, JsonElement>> entries = accountAsJsonObject.entrySet();

            for (Map.Entry<String, JsonElement> entry : entries) {
                account = new Gson().fromJson(entry.getValue(), Account.class);
                account.setName(entry.getKey());
            }
        } catch (DecodeException de) {
            logger.error(String.format("Error decoding object for '%s'", accountAsJsonObject));
            throw de;
        }

        return account;
    }
}