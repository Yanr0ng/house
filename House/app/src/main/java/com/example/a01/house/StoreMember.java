package com.example.a01.house;

public class StoreMember {

    private int sid;// { get; set; }
    private String suserid; //{ get; set; }
    private String spwd ;//{ get; set; }
    private String sname ;//{ get; set; }
    private String sphone; //{ get; set; }
    private String semail;// { get; set; }
    private String saddress;// { get; set; }
    private String shour;// { get; set; }

    private String sinf;//{ get; set; }
    private String sstatus;// { get; set; }
    private String sreport;// { get; set; }
    private String sphotoname;// { get; set; }
    private String sphotopath ;//{ get; set; }

    public StoreMember(int sid, String suserid, String spwd, String sname, String sphone, String semail, String saddress, String shour, String sinf, String sstatus, String sreport, String sphotoname, String sphotopath) {
        this.sid = sid;
        this.suserid = suserid;
        this.spwd = spwd;
        this.sname = sname;
        this.sphone = sphone;
        this.semail = semail;
        this.saddress = saddress;
        this.shour = shour;
        this.sinf = sinf;
        this.sstatus = sstatus;
        this.sreport = sreport;
        this.sphotoname = sphotoname;
        this.sphotopath = sphotopath;
    }
    public StoreMember() {
    }

    public int getSid() {
        return sid;
    }

    public String getSuserid() {
        return suserid;
    }

    public String getSpwd() {
        return spwd;
    }

    public String getSname() {
        return sname;
    }

    public String getSphone() {
        return sphone;
    }

    public String getSemail() {
        return semail;
    }

    public String getSaddress() {
        return saddress;
    }

    public String getShour() {
        return shour;
    }

    public String getSinf() {
        return sinf;
    }

    public String getSstatus() {
        return sstatus;
    }

    public String getSreport() {
        return sreport;
    }

    public String getSphotoname() {
        return sphotoname;
    }

    public String getSphotopath() {
        return sphotopath;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSuserid(String suserid) {
        this.suserid = suserid;
    }

    public void setSpwd(String spwd) {
        this.spwd = spwd;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public void setShour(String shour) {
        this.shour = shour;
    }

    public void setSinf(String sinf) {
        this.sinf = sinf;
    }

    public void setSstatus(String sstatus) {
        this.sstatus = sstatus;
    }

    public void setSreport(String sreport) {
        this.sreport = sreport;
    }

    public void setSphotoname(String sphotoname) {
        this.sphotoname = sphotoname;
    }

    public void setSphotopath(String sphotopath) {
        this.sphotopath = sphotopath;
    }
}
