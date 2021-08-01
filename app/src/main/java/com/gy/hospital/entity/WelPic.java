package com.gy.hospital.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cn.bmob.v3.BmobObject;

@Table(name = "WelPic")
public class WelPic extends BmobObject {

    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private Integer id;

    @Column(name = "url")
    private String url;

    @Column(name = "hospital")
    private com.gy.hospital.entity.hospital hospital;

    @Column(name = "auto")
    private String auto;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public com.gy.hospital.entity.hospital getHospital() {
        return hospital;
    }

    public void setHospital(com.gy.hospital.entity.hospital hospital) {
        this.hospital = hospital;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }
}
