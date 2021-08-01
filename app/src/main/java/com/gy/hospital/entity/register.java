package com.gy.hospital.entity;

import cn.bmob.v3.BmobObject;

public class register extends BmobObject {

    private com.gy.hospital.entity.department department;
    private String progress;
    private User user;
    private Integer view;

    public com.gy.hospital.entity.department getDepartment() {
        return department;
    }

    public void setDepartment(com.gy.hospital.entity.department department) {
        this.department = department;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }
}
