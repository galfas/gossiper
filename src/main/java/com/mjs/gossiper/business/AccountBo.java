package com.mjs.gossiper.business;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;

import java.io.IOException;

/**
 * Interface for the account business class.
 */
public interface AccountBo {

  void registerAccount(BasicAccount basicAccount) throws IOException;

  Account getAccount(BasicAccount basicAccount);

}
