package com.example.clothual.UI.welcome;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Repository.Welcome.AuthenticationRepository;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

import org.apache.commons.validator.routines.EmailValidator;

public class WelcomeModel extends ViewModel {

    public Application application;
    public AuthenticationRepository authenticationRepository;

    public WelcomeModel(Application application) {
        this.application = application;
        authenticationRepository = new AuthenticationRepository(application);

    }

    public void signUp(String email, String password, String surname, String name, String username){
        authenticationRepository.signUp(email, password, surname, name, username);
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

    public boolean isEmailOk(String email) {
        // Check if the email is valid through the use of this library:
        // https://commons.apache.org/proper/commons-validator/
        if (!EmailValidator.getInstance().isValid((email))) {
            //binding.textInputLayoutEmail.setError(getString(R.string.error_email));
            return false;
        } else {
            //binding.textInputLayoutEmail.setError(null);
            return true;
        }
    }

    public boolean isPasswordOk(String password) {
        // Check if the password length is correct
        if (password.isEmpty() || password.length() < 6) {
            //binding.textInputLayoutPassword.setError(getString(R.string.error_password));
            return false;
        } else {
           // binding.textInputLayoutPassword.setError(null);
            return true;
        }
    }



}
