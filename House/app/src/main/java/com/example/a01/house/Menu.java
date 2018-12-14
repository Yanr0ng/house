package com.example.a01.house;

public class Menu {
    private String mid;// { get; set; }
    private String suserid; //{ get; set; }
    private String mname;//{ get; set; }
    private String mprice; //{ get; set; }
    private String mphotoname;// { get; set; }
    private String mphonepath;// { get; set; }
    private String mqty;
    private String mtype;

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public Menu(String mtype) {
        this.mtype = mtype;

    }

    public Menu() {
    }

    public Menu(String mid, String suserid, String mname, String mprice, String mphotoname, String mphonepath, String mqty) {
        this.mid = mid;
        this.suserid = suserid;
        this.mname = mname;
        this.mprice = mprice;
        this.mphotoname = mphotoname;
        this.mphonepath = mphonepath;
        this.mqty = mqty;
    }

    public String getMid() {
        return mid;
    }

    public String getSuserid() {
        return suserid;
    }

    public String getMname() {
        return mname;
    }

    public String getMprice() {
        return mprice;
    }

    public String getMphotoname() {
        return mphotoname;
    }

    public String getMphonepath() {
        return mphonepath;
    }

    public String getMqty() {
        return mqty;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setSuserid(String suserid) {
        this.suserid = suserid;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public void setMphotoname(String mphotoname) {
        this.mphotoname = mphotoname;
    }

    public void setMphonepath(String mphonepath) {
        this.mphonepath = mphonepath;
    }

    public void setMqty(String mqty) {
        this.mqty = mqty;
    }
}