package com.bekaim.highping.models;

public class Spots {
    private Long user_id;

    public Spots(Long user_id) {
        this.user_id = user_id;
    }
    public Spots() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Spots{" +
                "user_id=" + user_id +
                '}';
    }
}
