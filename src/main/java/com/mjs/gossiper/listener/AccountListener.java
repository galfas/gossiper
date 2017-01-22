package com.mjs.gossiper.listener;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.domain.BasicAccount;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Listener to receive all the new player's registration
 */
public class AccountListener {

  @Autowired
  private AccountBo accountBo;

  public void handleAccount(BasicAccount basicAccount) throws IOException {
    accountBo.registerAccount(basicAccount);
  }
}
