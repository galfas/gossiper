package com.mjs.gossiper.gameProvider.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mjs.gossiper.builder.AccountBuilder;
import com.mjs.gossiper.builder.BasicAccountBuilder;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;
import com.mjs.gossiper.gameprovider.GameProvider;
import com.mjs.gossiper.gameprovider.contract.RiotProvider;
import com.mjs.gossiper.gameprovider.impl.GameProviderImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameProviderTest {

    @Mock
    private RiotProvider riotProvider;

    @InjectMocks
    GameProvider gameProvider = new GameProviderImpl();

    @Value("${riot.api.key}")
    private String apiRiotKey;

    @Test
    public void shouldFetchAccount() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("account1.json"), "UTF-8");

        JsonObject jsonFile = new Gson().fromJson(jsonAsString, JsonObject.class);

        BasicAccount basicAccount = BasicAccountBuilder.build("testName", "BRA");
        Account account = AccountBuilder.build();

        Mockito.when(riotProvider.getAccountForName(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(jsonFile);

        Account responseAccount = gameProvider.fetchAccountBy(basicAccount);

        Assert.assertNotNull(responseAccount);
        Assert.assertEquals(responseAccount.getId(), "64173720");
        Assert.assertEquals(responseAccount.getName(), "testName");
        Assert.assertEquals(responseAccount.getRegion(), "BRA");
        Assert.assertEquals(responseAccount.getProfileIconId(), "10");
        Assert.assertEquals(responseAccount.getRevisionDate(), Long.valueOf(1017817810000L));
        Assert.assertEquals(responseAccount.getSummonerLevel(), 2);
        Assert.assertNotNull(responseAccount.getLastUpdate());

    }

    @Test
    public void shouldReturnNullWhenFetchDoesNotFound() throws IOException {
        BasicAccount basicAccount = BasicAccountBuilder.build();

        Mockito.when(riotProvider.getAccountForName(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);

        Account account = gameProvider.fetchAccountBy(basicAccount);

        Assert.assertNull(account);
    }

    @Test
    public void shouldFetchStats() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        String jsonAsString = IOUtils.toString(classLoader.getResourceAsStream("stats.json"), "UTF-8");
        Feeds feeds = new Gson().fromJson(jsonAsString, Feeds.class);

        Account account = AccountBuilder.build();

        Mockito.when(riotProvider.getStatsFor(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(feeds);

        Feeds responseFeeds = gameProvider.getStats(account);

        Assert.assertNotNull(responseFeeds);
        Assert.assertEquals(responseFeeds.getSummonerId(), "27396167");
        Assert.assertEquals(responseFeeds.getGames().size(), 10);
        Assert.assertEquals(responseFeeds.getGames().get(0).getFellowPlayers().size(), 9);
        Assert.assertEquals(responseFeeds.getGames().get(0).getSpell1(), 7);
        Assert.assertEquals(responseFeeds.getGames().get(0).getSpell2(), 12);
    }

    @Test
    public void shouldReturnNullWhenDoesNotFindFetchStats() throws IOException {
        Account account = AccountBuilder.build();

        Mockito.when(riotProvider.getAccountForName(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);

        Feeds responseFeeds = gameProvider.getStats(account);

        Assert.assertNull(responseFeeds);
    }
}