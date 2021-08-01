package com.gy.hospital.entity;

public class DeptBean {

    private String title;
    private Integer icon;
    private String objectId;

    public DeptBean() {
    }

    public DeptBean(String title, Integer icon, String objectId) {
        this.title = title;
        this.icon = icon;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
