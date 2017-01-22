package com.mjs.gossiper.listener;

import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Listener to register the stats for a given player.
 */
public class StatsListener {

  @Autowired
  private StatsBo statsBo;

  public void handleStats(BasicAccount basicAccount) throws IOException {
    statsBo.registerStats(basicAccount);
  }
}
