package com.example.clothual.Data.Repository;

import android.app.Application;

import com.example.clothual.Data.Database.AccountDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

public class AuthenticationRepository {

    public Application application;

    public RoomDatabase database;
    private final UserDao userDao;
    private final AccountDao accountDao;

    public AuthenticationRepository(Application application){
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
    }

    //AccountDao

    public void insertAccount(Account account){
        accountDao.insertAccount(account);
    }

    public int getId(String username){
        return accountDao.getId(username);
    }

    //UserDao

    public void insertUser(User user){
        userDao.insertUser(user);
    }

}
