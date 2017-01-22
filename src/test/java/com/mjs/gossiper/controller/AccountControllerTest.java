package com.mjs.gossiper.controller;

import com.mjs.gossiper.builder.AccountBuilder;
import com.mjs.gossiper.builder.BasicAccountBuilder;
import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
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
public class AccountControllerTest {

    @Mock
    AccountBo accountBo;

    @Mock
    ActionPublisher actionPublisher;

    @InjectMocks
    AccountController accountController = new AccountController();

    @Test
    public void shouldCallAccountBoWithTheGivenParametersFromGetAccount(){
        String name = "testName";
        String region = "EUW";

        BasicAccount basicAccount = BasicAccountBuilder.build(name, region);
        Mockito.when(accountBo.getAccount(basicAccount)).thenReturn(AccountBuilder.build(name, region));

        Account account = accountController.getAccount(basicAccount);

        Assert.assertEquals(account.getName(), name);
        Assert.assertEquals(account.getRegion(), region);
        Mockito.verify(this.accountBo, Mockito.times(1)).getAccount(basicAccount);
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowTheReceivedExceptionFromGetAccount(){
        String name = "err";
        String region = "err";

        BasicAccount basicAccount = BasicAccountBuilder.build(name, region);
        Mockito.when(accountBo.getAccount(basicAccount)).thenThrow(new RuntimeException());

        accountController.getAccount(basicAccount);
    }

    @Test
    public void shouldCallAccountBoWithTheGivenParametersFromRegister() throws IOException {
        String name = "testName";
        String region = "EUW";

        BasicAccount basicAccount = BasicAccountBuilder.build(name, region);

        accountController.registerUser(basicAccount);

        Mockito.verify(actionPublisher, Mockito.times(1)).registerAccount(basicAccount);
    }

    @Test(expected=RuntimeException.class)
    public void shouldCallThrowTheReceivedException() throws IOException{
        String name = "err";
        String region = "err";

        BasicAccount basicAccount = BasicAccountBuilder.build(name, region);

        Mockito.doThrow(new RuntimeException()).when(actionPublisher).registerAccount(basicAccount);

        accountController.registerUser(basicAccount);
    }
}
