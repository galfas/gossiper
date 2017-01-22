package com.mjs.gossiper.gameprovider;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.Feeds;


public interface GameProvider {

  Account fetchAccountBy(BasicAccount basicAccount);

  Feeds getStats(Account account);
}
