package com.mjs.gossiper.publisher;

import com.mjs.gossiper.domain.BasicAccount;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service
@ManagedResource
public class ActionPublisher {

  @Autowired
  private AmqpTemplate template;

  @ManagedOperation
  public void registerAccount(BasicAccount basicAccount) {
    registerAccount("account.fanout", "actionQueue", basicAccount);
  }

  @ManagedOperation
  public void registerAccount(String exchange, String key, BasicAccount basicAccount) {
    template.convertAndSend(exchange, key, basicAccount);
  }

  @ManagedOperation
  public void registerStats(BasicAccount basicAccount) {
    registerStats("stats.fanout", "actionQueue", basicAccount);
  }

  @ManagedOperation
  public void registerStats(String exchange, String key, BasicAccount basicAccount) {
    template.convertAndSend(exchange, key, basicAccount);
  }
}

