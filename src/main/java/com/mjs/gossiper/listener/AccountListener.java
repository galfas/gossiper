package com.mjs.gossiper.listener;

import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.domain.BasicAccount;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Listener to receive all the new player's registration
 */
public class AccountListener {

  @Autowired
  private AccountBo accountBo;

  public void handleAccount(BasicAccount basicAccount) {
    accountBo.insert(basicAccount);
  }
}
