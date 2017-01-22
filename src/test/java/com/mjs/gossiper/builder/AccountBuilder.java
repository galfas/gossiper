package com.mjs.gossiper.builder;


import com.mjs.gossiper.domain.Account;

public class AccountBuilder {

    public static Account build(){
        return new Account("1111", "EUW", "testName", "10", 7,
                1436831731000L, 1485101554117L);
    }

    public static Account build(String name, String region){
        return new Account("1111", region, name, "10", 7,
                1436831731000L, 1485101554117L);
    }
}
