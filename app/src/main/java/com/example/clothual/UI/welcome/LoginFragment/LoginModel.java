package com.example.clothual.UI.welcome.LoginFragment;

import android.app.Application;

import com.example.clothual.Model.Account;
import com.example.clothual.Database.AccountDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Database.UserDao;

import java.util.List;

public class LoginModel {



    public Application application;
    public RoomDatabase database;
    private UserDao userDao;
    private AccountDao accountDao;

    public LoginModel(Application application) {
        this.application = application;
        //database = Room.databaseBuilder(application,
        //      RoomDatabase.class, DATABASE_NAME).build();
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
    }

    public boolean checkLogin(String username, String password){
        List<Account> account = accountDao.getAllAccount();
        for (int i = 0; i < account.size(); i++){
            if(account.get(i).getUsername().equals(username) && account.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public int idAccountByEmail(String email){
        return accountDao.getId(email);
    }

}
