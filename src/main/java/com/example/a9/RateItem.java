package com.example.a9;

public class RateItem {

    private int id;
    private String curname;
    private String currate;


    public RateItem() {
       super();
       curname="";
       currate="";
    }

    public RateItem(String curname, String rate) {
        super();
        this.curname = curname;
        this.currate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurname() {
        return curname;
    }

    public void setCurname(String curname) {
        this.curname = curname;
    }

    public String getCurrate() {
        return currate;
    }

    public void setCurrate(String rate) {
        this.currate = rate;
    }
}
