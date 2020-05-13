package com.bekaim.highping.models;

public class JoinedUser {
    private String user_id;
    private String card_id;

    public JoinedUser(String user_id, String card_id) {
        this.user_id = user_id;
        this.card_id = card_id;
    }

    public JoinedUser() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    @Override
    public String toString() {
        return "JoinedUser{" +
                "user_id='" + user_id + '\'' +
                ", card_id='" + card_id + '\'' +
                '}';
    }
}
