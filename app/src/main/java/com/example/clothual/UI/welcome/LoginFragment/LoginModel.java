package com.example.clothual.UI.welcome.LoginFragment;

import android.app.Application;

import com.example.clothual.Data.Database.AccountDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

public class LoginModel {



    public Application application;
    public RoomDatabase database;
    private UserDao userDao;
    private AccountDao accountDao;


    public LoginModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
    }


    public int getIDByEmail(String email){
        return accountDao.getIdByEmail(email);
    }


    public void createUser(String username, String name, String surname, String passowrd, String email){
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            Account account = new Account(username, email, passowrd);
            accountDao.insertAccount(account);
            User user = new User(surname, name, accountDao.getId(username));
            userDao.insertUser(user);
        });

    }

    public boolean userEsxiste(String username){
        if(accountDao.getAccountByUerName(username).getUsername().equals(username)){
            return true;
        }
        return false;
    }

}
