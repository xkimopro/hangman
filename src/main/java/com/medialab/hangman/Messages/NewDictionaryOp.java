package com.medialab.hangman.Messages;

import com.medialab.hangman.Dictionary;

public class NewDictionaryOp {
    int status;
    String msg;
    Dictionary dict;
    
    
    public NewDictionaryOp(int status, String msg, Dictionary dict){
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

    public void setStatusAndMsg(int status , String msg, Dictionary dict) {
        this.status = status; 
        this.msg = msg;
        this.dict = dict;
    }
}
