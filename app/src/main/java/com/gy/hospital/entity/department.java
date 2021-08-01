package com.gy.hospital.entity;

import cn.bmob.v3.BmobObject;

public class department extends BmobObject {

    private String auto;
    private User doctor;
    private com.gy.hospital.entity.hospital hospital;
    private Integer hot;
    private String name;
    private String place;

    public department() {
    }

    public department(String objectId) {
        this.setObjectId(objectId);
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public com.gy.hospital.entity.hospital getHospital() {
        return hospital;
    }

    public void setHospital(com.gy.hospital.entity.hospital hospital) {
        this.hospital = hospital;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
