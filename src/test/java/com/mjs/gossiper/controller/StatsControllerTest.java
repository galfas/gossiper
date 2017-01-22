package com.mjs.gossiper.controller;


import com.mjs.gossiper.builder.BasicAccountBuilder;
import com.mjs.gossiper.builder.PlayerStatBuilder;
import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.PlayerStat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatsControllerTest {

    @Mock
    StatsBo statsBo;

    @InjectMocks
    StatsController statsController = new StatsController();

    @Test
    public void shouldCallFetchTheStatistics(){
        String name = "testName";
        String region = "EUW";

        BasicAccount basicAccount = BasicAccountBuilder.build(name, region);
        Mockito.when(statsBo.getConsolidate(basicAccount)).thenReturn(PlayerStatBuilder.build());

        PlayerStat playerStat = statsController.getConsolidate(basicAccount);

        Assert.assertNotNull(playerStat);
        Mockito.verify(this.statsBo, Mockito.times(1)).getConsolidate(basicAccount);
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowExceptionWhileFetchingTheStatistics(){
        String name = "err";
        String region = "err";

        BasicAccount basicAccount = BasicAccountBuilder.build(name, region);
        Mockito.when(statsBo.getConsolidate(basicAccount)).thenThrow(new RuntimeException());

        statsController.getConsolidate(basicAccount);
    }
}