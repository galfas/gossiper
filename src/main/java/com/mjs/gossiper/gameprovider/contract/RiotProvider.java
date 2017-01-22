package com.mjs.gossiper.gameprovider.contract;

import com.google.gson.JsonObject;
import com.mjs.gossiper.domain.Feeds;
import feign.Param;
import feign.RequestLine;

public interface RiotProvider {

    @RequestLine("GET /api/lol/{region}/v1.4/summoner/by-name/{accountName}?api_key={key}")
    JsonObject getAccountForName(@Param("accountName") String accountName,
                                        @Param("region") String region,
                                        @Param("key") String key);

    @RequestLine("GET /api/lol/{region}/v1.3/game/by-summoner/{summonerId}/recent?api_key={key}")
    Feeds getStatsFor(@Param("summonerId") String summonerId,
                             @Param("region") String region,
                             @Param("key") String key);
}
