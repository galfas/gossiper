package com.mjs.gossiper.domain;

import org.mongodb.morphia.annotations.*;

import java.util.List;

@Entity("feeds")
@Indexes(
        @Index(value = "salary", fields = @Field("summonerId"))
)
public class Feeds {

    @Id
    private String summonerId;

    @Reference
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


