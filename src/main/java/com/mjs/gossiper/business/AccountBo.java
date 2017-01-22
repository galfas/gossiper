package com.mjs.gossiper.business;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;

/**
 * Interface for the account business class.
 */
public interface AccountBo {

  public void insert(BasicAccount basicAccount);

  public Account getAccount(BasicAccount basicAccount);

}
