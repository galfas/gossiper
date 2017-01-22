package com.mjs.gossiper.domain;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

public class Account {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String profileIconId;

    @NotNull
    private int summonerLevel;

    private String region;

    private Long revisionDate;

    private Long lastUpdate;


    public Account(String id, String region, String name, String profileIconId, int summonerLevel, Long revisionDate, Long lastUpdate) {
        this.id = id;
        this.region = region;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.lastUpdate = lastUpdate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(String profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public Long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long now) {
        this.lastUpdate = now;
    }

    public Account enrichWithLocale(BasicAccount basicAccount) {
        this.setRegion(basicAccount.getRegion());
        return this;
    }

    public Account enrichWithLastUpdate() {
        this.setLastUpdate(DateTime.now().getMillis());
        return this;
    }
}
