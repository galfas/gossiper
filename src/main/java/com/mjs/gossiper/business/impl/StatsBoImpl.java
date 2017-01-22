package com.mjs.gossiper.business.impl;

import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.dao.StatsRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;
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
    public Feeds getStats(BasicAccount basicAccount) {
        Feeds feeds = null;
        Account account = accountBo.getAccount(basicAccount);
        if(account!= null) {
            feeds = gameProvider.getStats(account);

            statsRepository.insert(feeds);
        }

        return feeds;
    }

    @Override
    public Feeds getConsolidate(BasicAccount basicAccount) {
        Feeds feeds = null;
        Account account = accountBo.getAccount(basicAccount);
        if(account != null) {
            feeds = statsRepository.getStats(account);
        }
        return feeds;
    }
}