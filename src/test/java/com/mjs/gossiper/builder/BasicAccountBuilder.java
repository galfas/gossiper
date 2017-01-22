package com.mjs.gossiper.builder;

import com.mjs.gossiper.domain.BasicAccount;

public class BasicAccountBuilder {

    public static BasicAccount build(){
        return new BasicAccount();
    }

    public static BasicAccount build(String name, String region){
        return new BasicAccount(name, region);
    }
}
