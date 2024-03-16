package com.developerrishit.contactsapp;

public class CallModel {
    String name,number,calltype,callduration,time;

    public CallModel(String name,String number, String calltype, String callduration, String time) {
        this.name = name;
        this.number = number;
        this.calltype = calltype;
        this.callduration = callduration;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public String getCallduration() {
        return callduration;
    }

    public void setCallduration(String callduration) {
        this.callduration = callduration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
