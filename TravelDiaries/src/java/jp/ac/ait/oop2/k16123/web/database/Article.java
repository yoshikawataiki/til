/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k16123.web.database;

import java.time.LocalDate;

/**
 *
 * @author yoshikawa
 */
public class Article {

    private int id;
    private int user_id;
    private String title;
    private String detail;
    private String image;
    private String category;
    private LocalDate created;

    public Article() {
    }

    public Article(int id, int user_id, String title, String detail, String image, String category, LocalDate created) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.detail = detail;
        this.image = image;
        this.category = category;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

}
