package com.example.a9;

public class Rate {
    private  String canme;
    private  String cavl;

    public String getCavl() {
        return cavl;
    }

    public void setCavl(String cavl) {
        this.cavl = cavl;
    }

    public String getCanme() {
        return canme;
    }

    public void setCanme(String canme) {
        this.canme = canme;
    }

    public Rate(String canme) {
        this.canme = canme;
    }
}
