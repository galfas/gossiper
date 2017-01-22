package com.mjs.gossiper.domain;

import java.util.List;

public class Game {
    private String gameId;
    private boolean invalid;
    private String gameMode;
    private String gameType;
    private String subType;
    private int mapId;
    private int teamId;
    private int championId;
    private int spell1;
    private int spell2;
    private int level;
    private String createDate;

    private List<FellowPlayer> fellowPlayers;

    private PlayerStats stats;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public int getSpell1() {
        return spell1;
    }

    public void setSpell1(int spell1) {
        this.spell1 = spell1;
    }

    public int getSpell2() {
        return spell2;
    }

    public void setSpell2(int spell2) {
        this.spell2 = spell2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<FellowPlayer> getFellowPlayers() {
        return fellowPlayers;
    }

    public void setFellowPlayers(List<FellowPlayer> fellowPlayers) {
        this.fellowPlayers = fellowPlayers;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }
}
