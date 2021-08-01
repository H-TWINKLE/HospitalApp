package com.gy.hospital.entity;

public class Info {

    private String title;

    private String val;

    public Info() {
    }

    public Info(String title, String val) {
        this.title = title;
        this.val = val;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
