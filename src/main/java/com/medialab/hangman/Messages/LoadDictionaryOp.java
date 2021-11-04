package com.medialab.hangman.Messages;

import com.medialab.hangman.Dictionary;

public class LoadDictionaryOp {
    int status;
    String msg;
    Dictionary dict;

    public LoadDictionaryOp(int status, String msg, Dictionary dict) {
        this.status = status;
        this.msg = msg;
        this.dict = dict;

    }

    public int getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    public Dictionary getDict() {
        return this.dict;
    }

    public void setStatusAndMsg(int status, String msg, Dictionary dict) {
        this.status = status;
        this.msg = msg;
        this.dict = dict;
    }
}
