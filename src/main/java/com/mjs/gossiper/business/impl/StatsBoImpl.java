package com.mjs.gossiper.business.impl;

import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.dao.StatsRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.PlayerStat;
import com.mjs.gossiper.gameprovider.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatsBoImpl implements StatsBo {

    @Autowired
    private AccountBo accountBo;

    @Autowired
    private GameProvider gameProvider;

    @Autowired
    private StatsRepository statsRepository;


    @Override
    public PlayerStat registerStats(BasicAccount basicAccount) {
        PlayerStat playerStat = null;
        Account account = accountBo.getAccount(basicAccount);
        if(account!= null) {
            playerStat = gameProvider.getStats(account);

            statsRepository.insert(playerStat);
        }

        return playerStat;
    }

    @Override
    public PlayerStat getConsolidate(BasicAccount basicAccount) {
        PlayerStat playerStat = null;
        Account account = accountBo.getAccount(basicAccount);
        if(account != null) {
            playerStat = statsRepository.getStats(account);
        }
        return playerStat;
    }
}