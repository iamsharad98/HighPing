package com.bekaim.highping.models;

public class UserProfile {
    private String name;
    private String username;
    private String gamename;
    private String user_id;
    private String email_id;
    private long total_kills;
    private long amount_won;
    private long curr_balance;
    private String mobile;


    public UserProfile() {
    }

    public UserProfile(String name, String username,
                       String gamename, String user_id, String email_id,
                       long total_kills, long amount_won, long curr_balance, String mobile) {
        this.name = name;
        this.username = username;
        this.gamename = gamename;
        this.user_id = user_id;
        this.email_id = email_id;
        this.total_kills = total_kills;
        this.amount_won = amount_won;
        this.curr_balance = curr_balance;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public long getTotal_kills() {
        return total_kills;
    }

    public void setTotal_kills(long total_kills) {
        this.total_kills = total_kills;
    }

    public long getAmount_won() {
        return amount_won;
    }

    public void setAmount_won(long amount_won) {
        this.amount_won = amount_won;
    }

    public long getCurr_balance() {
        return curr_balance;
    }

    public void setCurr_balance(long curr_balance) {
        this.curr_balance = curr_balance;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", gamename='" + gamename + '\'' +
                ", user_id='" + user_id + '\'' +
                ", email_id='" + email_id + '\'' +
                ", total_kills=" + total_kills +
                ", amount_won=" + amount_won +
                ", curr_balance=" + curr_balance +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
