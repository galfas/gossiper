package com.mjs.gossiper.business.impl;

import com.mjs.gossiper.dao.AccountRepository;
import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.gameprovider.GameProvider;
import com.mjs.gossiper.publisher.ActionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class AccountBoImpl implements AccountBo {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private GameProvider gameProvider;

  @Autowired
  private ActionPublisher actionPublisher;


  @Override
  public void registerAccount(BasicAccount basicAccount) throws IOException {
    Account account = gameProvider.fetchAccountBy(basicAccount);
    accountRepository.insert(account);

    actionPublisher.registerStats(basicAccount);
  }

  @Override
  public Account getAccount(BasicAccount basicAccount) {
    return accountRepository.getAccountFor(basicAccount);
  }
}

