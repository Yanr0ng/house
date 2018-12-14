package com.example.a01.house;

public class DetailList {
    private String mid;
    private String orderguid;
    private String mqty;
    private String cuserid;
    private String mname;
    private String mprice;
    private String cname;
    private String mtype;

    public DetailList(String mtype) {
        this.mtype = mtype;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public DetailList(String mname, String mprice, String cname) {
        this.mname = mname;
        this.mprice = mprice;
        this.cname = cname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMprice() {
        return mprice;
    }

    public String getCname() {
        return cname;
    }

    public DetailList() {
    }

    public DetailList(String mid, String orderguid, String mqty, String cuserid) {
        this.mid = mid;
        this.orderguid = orderguid;
        this.mqty = mqty;
        this.cuserid = cuserid;
    }

    public String getMid() {
        return mid;
    }

    public String getOrderguid() {
        return orderguid;
    }

    public String getMqty() {
        return mqty;
    }

    public String getCuserid() {
        return cuserid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setOrderguid(String orderguid) {
        this.orderguid = orderguid;
    }

    public void setMqty(String mqty) {
        this.mqty = mqty;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }
}
