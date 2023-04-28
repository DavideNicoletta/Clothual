package com.example.clothual.UI.welcome;

import android.app.Application;

import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Repository.AuthenticationRepository;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

public class WelcomeModel {

    public Application application;
    public AuthenticationRepository authenticationRepository;

    public WelcomeModel(Application application) {
        this.application = application;
        authenticationRepository = new AuthenticationRepository(application);

    }

    public void createUser(String username, String name, String surname, String passowrd, String email){
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            Account account = new Account(username, email, passowrd);
            authenticationRepository.insertAccount(account);
            //accountDao.insertAccount(account);
            User user = new User(surname, name, authenticationRepository.getId(username));
            authenticationRepository.insertUser(user);
            //userDao.insertUser(user);
        });

    }

    public int getIDByEmail(String email){
        return authenticationRepository.getIDByEmail(email);//accountDao.getIdByEmail(email);
    }

    public boolean userEsxiste(String username){
        if(authenticationRepository.getAccountByUerName(username).getUsername().equals(username)){
            return true;
        }
        return false;
    }


}
