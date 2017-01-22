package com.mjs.gossiper.business;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;

import java.io.IOException;

/**
 * Interface for the account business class.
 */
public interface AccountBo {

  public void insert(BasicAccount basicAccount) throws IOException;

  public Account getAccount(BasicAccount basicAccount);

}
