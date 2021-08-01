package com.gy.hospital.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class hospital extends BmobObject {

    private String auto;
    private String name;
    private String pic;
    private String place;
    private List<User> doctor;
    private List<com.gy.hospital.entity.department> department;

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<User> getDoctor() {
        return doctor;
    }

    public void setDoctor(List<User> doctor) {
        this.doctor = doctor;
    }

    public List<com.gy.hospital.entity.department> getDepartment() {
        return department;
    }

    public void setDepartment(List<com.gy.hospital.entity.department> department) {
        this.department = department;
    }
}
