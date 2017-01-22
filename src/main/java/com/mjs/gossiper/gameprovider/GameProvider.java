package com.mjs.gossiper.gameprovider;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.PlayerStat;

import java.io.IOException;


public interface GameProvider {

    Account fetchAccountBy(BasicAccount basicAccount) throws IOException;

    PlayerStat getStats(Account account);
}
