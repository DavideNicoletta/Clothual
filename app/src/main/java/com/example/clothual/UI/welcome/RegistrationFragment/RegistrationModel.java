package com.example.clothual.UI.welcome.RegistrationFragment;

import android.app.Application;

import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;
import com.example.clothual.Data.Database.AccountDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;

public class RegistrationModel {

    public Application application;
    public RoomDatabase database;
    private final UserDao userDao;
    private final AccountDao accountDao;

    public RegistrationModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
    }

    public void createUser(String username, String name, String surname, String passowrd, String email){
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            Account account = new Account(username, email, passowrd);
            accountDao.insertAccount(account);
            User user = new User(surname, name, accountDao.getId(username));
            userDao.insertUser(user);
        });

    }
}
