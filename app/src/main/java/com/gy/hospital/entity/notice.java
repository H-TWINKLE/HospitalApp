package com.gy.hospital.entity;

import cn.bmob.v3.BmobObject;

public class notice extends BmobObject {

    private User author;
    private String content;
    private String title;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
