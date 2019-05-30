package com.api.Models;

public class TimeObject {


    private Integer idPlayer;
    private Integer idContest;
    private Integer time;

    public TimeObject() {
    }

    public TimeObject(Integer idPlayer, Integer idContest, Integer time) {
        this.idPlayer = idPlayer;
        this.idContest = idContest;
        this.time = time;
    }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Integer getIdContest() {
        return idContest;
    }

    public void setIdContest(Integer idContest) {
        this.idContest = idContest;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}