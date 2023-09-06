package com.vn.matric.model;

public class HistoryModel {
    private String checkStatus;
    private int level;
    private String timeLeft;

    public HistoryModel(String checkStatus, int level, String timeLeft) {
        this.checkStatus = checkStatus;
        this.level = level;
        this.timeLeft = timeLeft;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }
}

