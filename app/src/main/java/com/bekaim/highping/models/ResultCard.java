package com.bekaim.highping.models;

import java.util.List;

public class ResultCard {

    private String image_url;
    private String match_count;
    private String time_stamp;
    private List<JoinedUser> joined_user;

    public ResultCard() {
    }

    public ResultCard(String image_url, String match_count, String time_stamp, List<JoinedUser> joined_user) {
        this.image_url = image_url;
        this.match_count = match_count;
        this.time_stamp = time_stamp;
        this.joined_user = joined_user;
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

    public List<JoinedUser> getJoined_user() {
        return joined_user;
    }

    public void setJoined_user(List<JoinedUser> joined_user) {
        this.joined_user = joined_user;
    }

    @Override
    public String toString() {
        return "ResultCard{" +
                "image_url='" + image_url + '\'' +
                ", match_count='" + match_count + '\'' +
                ", time_stamp='" + time_stamp + '\'' +
                ", joined_user=" + joined_user +
                '}';
    }
}
