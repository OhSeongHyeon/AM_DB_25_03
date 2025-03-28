package org.example.entity;

import java.time.LocalDateTime;

public class Article {
    private int id; //pk auto_increment
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String title;
    private String body;

    public Article() {
    }

    public Article(int id, LocalDateTime regDate, LocalDateTime updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", regDate=" + regDate +
                ", updateDate=" + updateDate +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
