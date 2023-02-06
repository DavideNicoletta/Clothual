package com.example.clothual.UI.core;

import android.app.Application;

import com.example.clothual.Database.AccountDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Database.UserDao;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

public class CoreModel {

    public Application application;
    public RoomDatabase database;
    private final UserDao userDao;
    private final AccountDao accountDao;

    public CoreModel(Application application) {
        this.application = application;
        //database = Room.databaseBuilder(application,
        //      RoomDatabase.class, DATABASE_NAME).build();
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
    }

    public void createUserGoogle(String username, String name, String surname, String email){
        Account account = new Account(username, email, "null");
        accountDao.insertAccount(account);
        User user = new User(surname, name, accountDao.getId(username));
        userDao.insertUser(user);
    }

}


