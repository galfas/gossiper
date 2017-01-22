package com.mjs.gossiper.domain;

import java.util.List;

public class PlayerStat {

    private String summonerId;

    private List<Game> games;

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}


