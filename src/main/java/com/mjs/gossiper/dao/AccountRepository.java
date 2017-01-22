package com.mjs.gossiper.dao;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;


public interface AccountRepository {

  public Account insert(Account account);

  public Account getAccountFor(BasicAccount basicAccount);
}
