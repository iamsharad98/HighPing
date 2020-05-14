package com.bekaim.highping.models;

public class ResultPlayerList {
    private String player_num;
    private String player_name;
    private String kills;
    private String winning;
    private String match_count;
    private String match_time;
    private String win_prize;
    private String per_kill;
    private String entry_fee;


    public ResultPlayerList() {
    }

    public ResultPlayerList(String player_num, String player_name, String kills,
                            String winning, String match_count, String match_time, String win_prize, String per_kill, String entry_fee) {
        this.player_num = player_num;
        this.player_name = player_name;
        this.kills = kills;
        this.winning = winning;
        this.match_count = match_count;
        this.match_time = match_time;
        this.win_prize = win_prize;
        this.per_kill = per_kill;
        this.entry_fee = entry_fee;
    }

    public String getMatch_count() {
        return match_count;
    }

    public void setMatch_count(String match_count) {
        this.match_count = match_count;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getWin_prize() {
        return win_prize;
    }

    public void setWin_prize(String win_prize) {
        this.win_prize = win_prize;
    }

    public String getPer_kill() {
        return per_kill;
    }

    public void setPer_kill(String per_kill) {
        this.per_kill = per_kill;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getPlayer_num() {
        return player_num;
    }

    public void setPlayer_num(String player_num) {
        this.player_num = player_num;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getKills() {
        return kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }

    public String getWinning() {
        return winning;
    }

    public void setWinning(String winning) {
        this.winning = winning;
    }

    @Override
    public String toString() {
        return "ResultPlayerList{" +
                "player_num='" + player_num + '\'' +
                ", player_name='" + player_name + '\'' +
                ", kills='" + kills + '\'' +
                ", winning='" + winning + '\'' +
                ", match_count='" + match_count + '\'' +
                ", match_time='" + match_time + '\'' +
                ", win_prize='" + win_prize + '\'' +
                ", per_kill='" + per_kill + '\'' +
                ", entry_fee='" + entry_fee + '\'' +
                '}';
    }
}
