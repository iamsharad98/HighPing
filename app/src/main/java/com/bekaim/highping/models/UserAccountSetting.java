package com.bekaim.highping.models;

public class UserAccountSetting {
    private String user_id;
    private String gamename;
    private long mobile;
    private String email;

    public UserAccountSetting(String user_id, String gamename, long mobile, String email) {
        this.user_id = user_id;
        this.gamename = gamename;
        this.mobile = mobile;
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserAccountSetting{" +
                "user_id='" + user_id + '\'' +
                ", gamename='" + gamename + '\'' +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                '}';
    }
}