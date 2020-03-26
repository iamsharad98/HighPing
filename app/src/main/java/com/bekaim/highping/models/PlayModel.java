package com.bekaim.highping.models;

import java.util.List;

public class PlayModel {

    private String image_url;
    private String match_count;
    private String time_stamp;
    private String win_prize;
    private String match_type;
    private String amt_per_kill;
    private String version;
    private String entry_fee;
    private String map;
    private String card_id;
    private String room_id;
    private String room_password;
    private List<Spots> spots_left;

    public PlayModel() {
    }

    public PlayModel(String image_url, String match_count, String time_stamp, String win_prize,
                     String match_type, String amt_per_kill, String version,
                     String entry_fee, String map, String card_id, String room_id,
                     String room_password, List<Spots> spots_left) {
        this.image_url = image_url;
        this.match_count = match_count;
        this.time_stamp = time_stamp;
        this.win_prize = win_prize;
        this.match_type = match_type;
        this.amt_per_kill = amt_per_kill;
        this.version = version;
        this.entry_fee = entry_fee;
        this.map = map;
        this.card_id = card_id;
        this.room_id = room_id;
        this.room_password = room_password;
        this.spots_left = spots_left;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_password() {
        return room_password;
    }

    public void setRoom_password(String room_password) {
        this.room_password = room_password;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMatch_count() {
        return match_count;
    }

    public void setMatch_count(String match_count) {
        this.match_count = match_count;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getWin_prize() {
        return win_prize;
    }

    public void setWin_prize(String win_prize) {
        this.win_prize = win_prize;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getAmt_per_kill() {
        return amt_per_kill;
    }

    public void setAmt_per_kill(String amt_per_kill) {
        this.amt_per_kill = amt_per_kill;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public List<Spots> getSpots_left() {
        return spots_left;
    }

    public void setSpots_left(List<Spots> spots_left) {
        this.spots_left = spots_left;
    }

    @Override
    public String toString() {
        return "PlayModel{" +
                "image_url='" + image_url + '\'' +
                ", match_count='" + match_count + '\'' +
                ", time_stamp='" + time_stamp + '\'' +
                ", win_prize='" + win_prize + '\'' +
                ", match_type='" + match_type + '\'' +
                ", amt_per_kill='" + amt_per_kill + '\'' +
                ", version='" + version + '\'' +
                ", entry_fee='" + entry_fee + '\'' +
                ", map='" + map + '\'' +
                ", card_id='" + card_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", room_password='" + room_password + '\'' +
                ", spots_left=" + spots_left +
                '}';
    }
}
