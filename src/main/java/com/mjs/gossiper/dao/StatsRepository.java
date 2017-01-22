package com.mjs.gossiper.dao;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.PlayerStat;

public interface StatsRepository {

    PlayerStat insert(PlayerStat playerStat);

    PlayerStat getStats(Account account);
}
