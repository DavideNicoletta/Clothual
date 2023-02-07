package com.example.clothual.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Clothual {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int type;
    private String brand;
    private String description;
    private String color;
    private String template;
    private int idImage;
    private boolean isPreferite;

    private int idUserAccount;

    public Clothual(int type, String brand, String description, String color, String template, int idImage, int idUserAccount) {
        this.type = type;
        this.brand = brand;
        this.description = description;
        this.color = color;
        this.template = template;
        this.idImage = idImage;
        this.isPreferite = false;
        this.idUserAccount = idUserAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public boolean isPreferite() {
        return isPreferite;
    }

    public void setPreferite(boolean preferite) {
        isPreferite = preferite;
    }

    public int getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(int idUserAccount) {
        this.idUserAccount = idUserAccount;
    }



}
