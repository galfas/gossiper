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
  public void send(BasicAccount basicAccount) {
    send("amq.fanout", "actionQueue", basicAccount);
  }

  @ManagedOperation
  public void send(String exchange, String key,  BasicAccount basicAccount) {
    template.convertAndSend(exchange, key, basicAccount);
  }
}

