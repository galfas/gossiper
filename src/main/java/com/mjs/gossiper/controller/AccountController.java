package com.mjs.gossiper.controller;

import com.mjs.gossiper.business.AccountBo;
import com.mjs.gossiper.domain.Account;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.publisher.ActionPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountBo accountBo;

  @Autowired
  private ActionPublisher actionPublisher;

  @RequestMapping(method = RequestMethod.GET)
  public Account getAccount(BasicAccount basicAccount){
    return accountBo.getAccount(basicAccount);
  }

  @RequestMapping(method = RequestMethod.POST)
  public void registerUser(@RequestBody BasicAccount basicAccount) throws IOException {
    actionPublisher.send(basicAccount);
  }
}
