package com.example.a01.house;

import java.util.Date;

public class OrderList {
    private int oid;
    private String orderguid;
    private String receiver;
    private String phone;
    private String address;
    private Date odate;
    private String suserid;
    private String duserid;
    private String ostatus;
    private String note;
    private String orderdate;
    private String cuserid;

    public OrderList(String cuserid) {
        this.cuserid = cuserid;
    }

    public String getCuserid() {
        return cuserid;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    private String isapproved;

    public OrderList() {
    }

    public OrderList(int oid,String orderdate, String orderguid, String receiver, String phone, String address, Date odate, String suserid, String duserid, String ostatus, String note, String isapproved) {
        this.oid = oid;
        this.orderguid = orderguid;
        this.receiver = receiver;
        this.phone = phone;
        this.address = address;
        this.odate = odate;
        this.suserid = suserid;
        this.duserid = duserid;
        this.ostatus = ostatus;
        this.note = note;
        this.orderdate=orderdate;
        this.isapproved = isapproved;
    }

    public int getOid() {
        return oid;
    }

    public String getOrderguid() {
        return orderguid;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Date getOdate() {
        return odate;
    }

    public String getSuserid() {
        return suserid;
    }

    public String getDuserid() {
        return duserid;
    }

    public String getOstatus() {
        return ostatus;
    }

    public String getNote() {
        return note;
    }


    public String getIsapproved() {
        return isapproved;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public void setOrderguid(String orderguid) {
        this.orderguid = orderguid;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public void setSuserid(String suserid) {
        this.suserid = suserid;
    }

    public void setDuserid(String duserid) {
        this.duserid = duserid;
    }

    public void setOstatus(String ostatus) {
        this.ostatus = ostatus;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public void setIsapproved(String isapproved) {
        this.isapproved = isapproved;
    }
}
