package com.example.clothual.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    private String surname;
    private String name;
    private int idAccount;


    public User(String surname, String name, int idAccount, String id) {
        this.surname = surname;
        this.name = name;
        this.idAccount = idAccount;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
               // ", idAccount=" + idAccount +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
