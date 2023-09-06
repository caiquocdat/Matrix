package com.vn.matric.model;

public class HistoryModel {
    private String checkWin;
    private int level;
    private String time;

    public HistoryModel(String checkWin, int level, String time) {
        this.checkWin = checkWin;
        this.level = level;
        this.time = time;
    }

    public String getCheckStatus() {
        return checkWin;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkWin = checkStatus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTimeLeft() {
        return time;
    }

    public void setTimeLeft(String timeLeft) {
        this.time = timeLeft;
    }
}

