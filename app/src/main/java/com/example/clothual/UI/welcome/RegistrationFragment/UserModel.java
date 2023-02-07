package com.example.clothual.UI.welcome.RegistrationFragment;

public class UserModel {
    private String email, surname, name, username;


    public UserModel(String username, String name, String surname, String email){
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
