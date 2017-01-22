package com.mjs.gossiper.business.impl;

import com.mjs.gossiper.builder.AccountBuilder;
import com.mjs.gossiper.builder.BasicAccountBuilder;
import com.mjs.gossiper.builder.PlayerStatBuilder;
import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.dao.StatsRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.PlayerStat;
import com.mjs.gossiper.gameprovider.GameProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatsBoTest{

    @Mock
    private AccountBo accountBo;

    @Mock
    private GameProvider gameProvider;

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    StatsBo statsBo = new StatsBoImpl();

    @Test
    public void shouldInsertTheStatistics(){
        BasicAccount basicAccount = BasicAccountBuilder.build();
        Account account = AccountBuilder.build();
        PlayerStat playerStat = PlayerStatBuilder.build();

        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(account);
        Mockito.when(gameProvider.getStats(account)).thenReturn(playerStat);

        PlayerStat responsePlayerStat = statsBo.registerStats(basicAccount);

        Assert.assertNotNull(responsePlayerStat);
        Assert.assertEquals(playerStat, responsePlayerStat);
        Mockito.verify(statsRepository, Mockito.times(1)).insert(playerStat);
    }

    @Test
    public void shouldNotInsertStatsWhenTheAccountIsNotFound(){
        BasicAccount basicAccount = BasicAccountBuilder.build();
        Account account = AccountBuilder.build();
        PlayerStat playerStat = PlayerStatBuilder.build();

        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(null);
        Mockito.when(gameProvider.getStats(account)).thenReturn(playerStat);

        PlayerStat responsePlayerStat = statsBo.registerStats(basicAccount);

        Assert.assertNull(responsePlayerStat);
        Mockito.verify(gameProvider, Mockito.times(0)).getStats(account);
        Mockito.verify(statsRepository, Mockito.times(0)).insert(playerStat);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowTheReceivedException(){
        BasicAccount basicAccount = BasicAccountBuilder.build();
        Account account = AccountBuilder.build();
        PlayerStat playerStat = PlayerStatBuilder.build();

        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(account);
        Mockito.when(gameProvider.getStats(account)).thenThrow(new RuntimeException());

        statsBo.registerStats(basicAccount);
        Mockito.verify(statsRepository, Mockito.times(0)).insert(playerStat);
    }

    @Test
    public void shouldGetConsolidate(){
        BasicAccount basicAccount = BasicAccountBuilder.build();
        Account account = AccountBuilder.build();
        PlayerStat playerStat = PlayerStatBuilder.build();

        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(account);
        Mockito.when(statsRepository.getStats(account)).thenReturn(playerStat);

        PlayerStat responsePlayerStat = statsBo.getConsolidate(basicAccount);

        Assert.assertNotNull(responsePlayerStat);
        Assert.assertEquals(playerStat, responsePlayerStat);

        Mockito.verify(accountBo, Mockito.times(1)).getAccount(basicAccount);
        Mockito.verify(statsRepository, Mockito.times(1)).getStats(account);
    }

    @Test
    public void shouldReturnNulWhenTheAccountIsNotFound(){
        BasicAccount basicAccount = BasicAccountBuilder.build();
        Account account = AccountBuilder.build();

        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(null);

        PlayerStat responsePlayerStat = statsBo.getConsolidate(basicAccount);

        Assert.assertNull(responsePlayerStat);

        Mockito.verify(accountBo, Mockito.times(1)).getAccount(basicAccount);
        Mockito.verify(statsRepository, Mockito.times(0)).getStats(account);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenItIsReceivedFromConsolidateRepository(){
        BasicAccount basicAccount = BasicAccountBuilder.build();
        Account account = AccountBuilder.build();

        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(account);
        Mockito.when(statsRepository.getStats(account)).thenThrow(new RuntimeException());

        statsBo.getConsolidate(basicAccount);

        Mockito.verify(accountBo, Mockito.times(1)).getAccount(basicAccount);
        Mockito.verify(statsRepository, Mockito.times(1)).getStats(account);
    }
}