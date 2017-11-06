package com.example.Help;

import java.io.Serializable;

/**
 * Created by hh on 2017/8/2.
 */

public class PostInfo implements Serializable {
    private String author;
    private String title;
    private String content;
    private String date;

    private int postId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public PostInfo(String author, String title, String content, String date,int postId){
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
