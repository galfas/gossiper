package com.mjs.gossiper.dao;

import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;


public interface AccountRepository {

  Account insert(Account account);

  Account getAccountFor(BasicAccount basicAccount);
}
