package com.sem.e_health;

public class Test {
    String temp ;
    String Hartbeats ;
    String Emg ;
    String Glucose ;
    private boolean expanded;
    String time ;

    public Test(String temp, String hartbeats,String emg ,String glucose ) {
        this.temp = temp;
        Hartbeats = hartbeats;
        Emg=emg;
        Glucose=glucose;

    }

    public String getEmg() {
        return Emg;
    }

    public void setEmg(String emg) {
        Emg = emg;
    }

    public Test() {
    }

    public String getGlucose() {
        return Glucose;
    }

    public void setGlucose(String glucose) {
        Glucose = glucose;
    }

    public String getTemp() {
        return temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHartbeats() {
        return Hartbeats;
    }

    public void setHartbeats(String hartbeats) {
        Hartbeats = hartbeats;
    }
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
