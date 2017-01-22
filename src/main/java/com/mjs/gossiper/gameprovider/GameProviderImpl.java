package com.mjs.gossiper.gameprovider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;
import feign.Feign;
import feign.FeignException;
import feign.Param;
import feign.RequestLine;
import feign.codec.DecodeException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.ws.http.HTTPException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class GameProviderImpl implements GameProvider {

    private static final Logger logger = LoggerFactory.getLogger(GameProviderImpl.class);

    private static final OkHttpClient client = configureClient();

    @Value("${riot.api.timeout}")
    private static int TIME_OUT;

    @Value("${riot.api.host}")
    private String apiEndpoint;

    @Value("${riot.api.key}")
    private String apiRiotKey;

    @Override
    public Account fetchAccountBy(BasicAccount basicAccount) {
        logger.debug(String.format("The fetch method of data will be performed with the follow parameters '%s'", basicAccount));

        //TODO should be used as component
        AccountProvider accountProvider = buildAccountProvider();

        JsonObject accountAsJson = null;
        try {
            accountAsJson = accountProvider.getAccountForName(basicAccount.getName(), basicAccount.getRegion(), apiRiotKey);
        } catch (HTTPException ex) {
            logger.error(String.format("Error fetching account for '%s'", basicAccount));
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
        StatsProvider statsProvider = buildStatsProvider();
        try {
            feeds = statsProvider.getStatsFor(account.getId(), account.getRegion(), apiRiotKey);
        } catch (FeignException ex) {
            logger.error(String.format("Error fetching account for '%s' the cause was ", account.getId(), ex.getCause()));
            throw ex;
        } catch (Exception ex){
            logger.error(String.format("UnexpError fetching account for '%s'", account.getId()));
            throw ex;
        }

        return feeds;
    }

    private interface AccountProvider {
        @RequestLine("GET /api/lol/{region}/v1.4/summoner/by-name/{accountName}?api_key={key}")
        public JsonObject getAccountForName(@Param("accountName") String accountName,
                                            @Param("region") String region,
                                            @Param("key") String key);
    }

    private interface StatsProvider {
        @RequestLine("GET /api/lol/{region}/v1.3/game/by-summoner/{summonerId}/recent?api_key={key}")
        public Feeds getStatsFor(@Param("summonerId") String summonerId,
                                 @Param("region") String region,
                                 @Param("key") String key);
    }

    private Account jsonToAccount(JsonObject accountAsJsonObject) {
        Account account = null;

        try{
            Set<Map.Entry<String, JsonElement>> entries = accountAsJsonObject.entrySet();

            for (Map.Entry<String, JsonElement> entry : entries) {
                account = new Gson().fromJson(entry.getValue(), Account.class);
                account.setName(entry.getKey());
            }
        }catch (DecodeException de){
            logger.error(String.format("Error decoding object for '%s'", accountAsJsonObject));
            throw de;
        }

        return account;
    }

    /**
     * Configure the HTTP client.
     *
     * @return a configured HTTP pool.
     */
    private static OkHttpClient configureClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    /**
     * @return Feign configured provider
     */
    private AccountProvider buildAccountProvider() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .client(new feign.okhttp.OkHttpClient(client))
                .target(AccountProvider.class, apiEndpoint);
    }

    private StatsProvider buildStatsProvider() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .client(new feign.okhttp.OkHttpClient(client))
                .target(StatsProvider.class, apiEndpoint);
    }
}