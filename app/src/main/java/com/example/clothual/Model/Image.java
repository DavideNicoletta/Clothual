package com.example.clothual.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Image {

    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String title;
    private String description;
    private String uri;

    private String idUser;

    public Image(String title, String description, String uri, String idUser) {
        this.title = title;
        this.description = description;
        this.uri = uri;
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String  idUser) {
        this.idUser = idUser;
    }
}
