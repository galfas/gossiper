package com.mjs.gossiper.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * This model represents the minimum data for a valid account
 */
public class BasicAccount implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String region;

    public BasicAccount() {}

    public BasicAccount(String name, String region) {
        this.name = name;
        this.region = region;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "BasicAccount{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
