package com.mjs.gossiper.business;

import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;

/**
 * Interface for the stats business class.
 */
public interface StatsBo {

    Feeds registerStats(BasicAccount basicAccount);

    Feeds getConsolidate(BasicAccount basicAccount);
}
