package com.mjs.gossiper.controller;


import com.mjs.gossiper.builder.FeedsBuilder;
import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatsControllerTest {

    @Mock
    StatsBo statsBo;

    @InjectMocks
    StatsController statsController = new StatsController();


    @RequestMapping(method = RequestMethod.GET)
    public Feeds getAccount(BasicAccount basicAccount){
        return statsBo.getStats(basicAccount);
    }

    @Test
    public void shouldCallStatsBoWithTheGivenParameters(){
        String name = "testName";
        String region = "EUW";

        BasicAccount basicAccount = new BasicAccount(name, region);
        Mockito.when(statsBo.getStats(basicAccount)).thenReturn(FeedsBuilder.build());

        Feeds feeds = statsController.getStats(basicAccount);

        Assert.assertNotNull(feeds);
        Mockito.verify(this.statsBo, Mockito.times(1)).getStats(basicAccount);
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowTheReceivedException(){
        String name = "err";
        String region = "err";

        BasicAccount basicAccount = new BasicAccount(name, region);
        Mockito.when(statsBo.getStats(basicAccount)).thenThrow(new RuntimeException());

        statsController.getStats(basicAccount);
    }

    public Feeds getConsolidate(BasicAccount basicAccount){
        return statsBo.getConsolidate(basicAccount);
    }

    @Test
    public void shouldCallFetchTheStatistics(){
        String name = "testName";
        String region = "EUW";

        BasicAccount basicAccount = new BasicAccount(name, region);
        Mockito.when(statsBo.getConsolidate(basicAccount)).thenReturn(FeedsBuilder.build());

        Feeds feeds = statsController.getConsolidate(basicAccount);

        Assert.assertNotNull(feeds);
        Mockito.verify(this.statsBo, Mockito.times(1)).getConsolidate(basicAccount);
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowExceptionWhileFetchingTheStatistics(){
        String name = "err";
        String region = "err";

        BasicAccount basicAccount = new BasicAccount(name, region);
        Mockito.when(statsBo.getConsolidate(basicAccount)).thenThrow(new RuntimeException());

        statsController.getConsolidate(basicAccount);
    }
}