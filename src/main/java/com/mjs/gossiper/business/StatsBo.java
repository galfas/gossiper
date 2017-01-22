package com.mjs.gossiper.business;

import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.PlayerStat;

/**
 * Interface for the stats business class.
 */
public interface StatsBo {

    PlayerStat registerStats(BasicAccount basicAccount);

    PlayerStat getConsolidate(BasicAccount basicAccount);
}
