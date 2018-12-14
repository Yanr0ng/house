package com.example.a01.house;

public class Category {
    private String cateid; //{ get; set; }

    public Category() {
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getSclass() {
        return sclass;
    }

    private String sclass ;//{ get; set; }
}
