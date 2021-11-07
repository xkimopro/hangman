package com.medialab.hangman.Messages;

import com.medialab.hangman.Dictionary;

import java.util.ArrayList;

public class LoadStatsOp {
    int status;
    String msg;
    ArrayList<String> stats;

    public LoadStatsOp(int status, String msg, ArrayList<String> stats) {
        this.status = status;
        this.msg = msg;
        this.stats = stats;

    }

    public int getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    public ArrayList<String> getStats() {
        return this.stats;
    }

    public void setStatusAndMsg(int status, String msg, ArrayList<String> dict) {
        this.status = status;
        this.msg = msg;
        this.stats = stats;
    }
}
