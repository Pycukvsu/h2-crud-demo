package com.company;

import java.util.Arrays;
import java.util.List;

public class Player {

    private int playerId;
    private String nickname;
    private Progresses[] progresses;
    private Currencies[] currencies;
    private Items[] items;

    public Player(int playerId, String nickname, Progresses[] progresses, Currencies[] currencies, Items[] items) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.progresses = progresses;
        this.currencies = currencies;
        this.items = items;
    }

    public Player(int playerId, String nickname, Progresses[] progresses) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.progresses = progresses;
    }

    public Player(int playerId, String nickname, Progresses[] progresses, Currencies[] currencies) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.progresses = progresses;
        this.currencies = currencies;
    }

    public Player() {
    }

    public Player(int playerId, String nickname) {
        this.playerId = playerId;
        this.nickname = nickname;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Progresses[] getProgresses() {
        return progresses;
    }

    public void setProgresses(Progresses[] progresses) {
        this.progresses = progresses;
    }

    public Currencies[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies[] currencies) {
        this.currencies = currencies;
    }

    public Items[] getItems() {
        return items;
    }

    public void setItems(Items[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", nickname='" + nickname + '\'' +
                ", progresses=" + Arrays.toString(progresses) +
                ", currencies=" + Arrays.toString(currencies) +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}
