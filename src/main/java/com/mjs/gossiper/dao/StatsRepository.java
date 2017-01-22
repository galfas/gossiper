package com.mjs.gossiper.dao;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.Feeds;

public interface StatsRepository {

    public Feeds insert(Feeds feeds);

    Feeds getStats(Account account);
}
