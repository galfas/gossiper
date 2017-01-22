package com.mjs.gossiper.business.impl;

import com.mjs.gossiper.builder.AccountBuilder;
import com.mjs.gossiper.builder.BasicAccountBuilder;
import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.dao.AccountRepository;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.gameprovider.GameProvider;
import com.mjs.gossiper.publisher.ActionPublisher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
public class AccountBoTest {

  @Mock
  AccountRepository accountRepository;

  @Mock
  GameProvider gameProvider;

  @Mock
  ActionPublisher actionPublisher;

  @InjectMocks
  AccountBo accountBo = new AccountBoImpl();

  @Test
  public void shouldInsertAccountWhenItIsFound() throws IOException {
    BasicAccount basicAccount = BasicAccountBuilder.build();
    Account account = AccountBuilder.build();

    Mockito.when(gameProvider.fetchAccountBy(basicAccount)).thenReturn(account);
    Mockito.when(accountRepository.insert(account)).thenReturn(account);

    accountBo.registerAccount(basicAccount);

    Mockito.verify(gameProvider, Mockito.times(1)).fetchAccountBy(basicAccount);
    Mockito.verify(accountRepository, Mockito.times(1)).insert(account);
    Mockito.verify(actionPublisher, Mockito.times(1)).registerStats(basicAccount);
  }

  @Test(expected=RuntimeException.class)
  public void shouldNotInsertThrowExceptionWhenItReceivedFromGameProvider() throws IOException {
    BasicAccount basicAccount = BasicAccountBuilder.build();
    Account account = AccountBuilder.build();

    Mockito.when(gameProvider.fetchAccountBy(basicAccount)).thenThrow(new RuntimeException());

    accountBo.registerAccount(basicAccount);

    Mockito.verify(accountRepository, Mockito.times(0)).insert(Mockito.any());
    Mockito.verify(actionPublisher, Mockito.times(0)).registerStats(basicAccount);
  }

  @Test(expected=RuntimeException.class)
  public void shouldNotInsertThrowExceptionWhenItReceivedFromRepository() throws IOException {
    BasicAccount basicAccount = BasicAccountBuilder.build();
    Account account = AccountBuilder.build();

    Mockito.when(gameProvider.fetchAccountBy(basicAccount)).thenReturn(account);
    Mockito.when(accountRepository.insert(Mockito.any())).thenThrow(new RuntimeException());

    accountBo.registerAccount(basicAccount);

    Mockito.verify(actionPublisher, Mockito.times(0)).registerStats(basicAccount);
  }

  @Test
  public void shouldReturnAccountWhenItIsFound() throws IOException {

    BasicAccount basicAccount = BasicAccountBuilder.build();
    Account account = AccountBuilder.build();

    Mockito.when(accountRepository.getAccountFor(basicAccount)).thenReturn(account);

    Account responseAccount = accountBo.getAccount(basicAccount);

    Assert.assertNotNull(responseAccount);
    Assert.assertEquals(responseAccount, account);
    Mockito.verify(accountRepository, Mockito.times(1)).getAccountFor(basicAccount);
  }

  @Test(expected=RuntimeException.class)
  public void shouldThrowExceptionWhenItReceivedFromGetAccount() throws IOException {
    BasicAccount basicAccount = BasicAccountBuilder.build();
    Account account = AccountBuilder.build();

    Mockito.when(accountRepository.getAccountFor(basicAccount)).thenThrow(new RuntimeException());

    accountBo.getAccount(basicAccount);

  }
}

