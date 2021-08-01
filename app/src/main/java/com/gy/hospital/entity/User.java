package com.gy.hospital.entity;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String auto;
    private String headerPic;
    private String identity;
    private String nickname;
    private String pass;
    private Integer sex;
    private Integer plate;
    private com.gy.hospital.entity.hospital hospital;
    private com.gy.hospital.entity.department department;

    public User(String objectId) {
        this.setObjectId(objectId);
    }

    public User() {
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getHeaderPic() {
        return headerPic;
    }

    public void setHeaderPic(String headerPic) {
        this.headerPic = headerPic;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getPlate() {
        return plate;
    }

    public void setPlate(Integer plate) {
        this.plate = plate;
    }

    public com.gy.hospital.entity.hospital getHospital() {
        return hospital;
    }

    public void setHospital(com.gy.hospital.entity.hospital hospital) {
        this.hospital = hospital;
    }

    public com.gy.hospital.entity.department getDepartment() {
        return department;
    }

    public void setDepartment(com.gy.hospital.entity.department department) {
        this.department = department;
    }
}


