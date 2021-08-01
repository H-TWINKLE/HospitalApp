package com.gy.hospital.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Message implements MultiItemEntity {

    public static final int AS_ROBOT = 1;

    public static final int AS_USER = 2;

    private String head;

    private Integer type;

    private String content;

    public Message(String head, Integer type, String content) {
        this.head = head;
        this.type = type;
        this.content = content;
    }

    public Message() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return getType();
    }
}
