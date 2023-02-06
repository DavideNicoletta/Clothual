package com.example.clothual.UI.welcome.LoginFragment;

import android.app.Application;
import android.util.Log;

import com.example.clothual.Model.Account;
import com.example.clothual.Database.AccountDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Database.UserDao;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.List;

public class LoginModel {



    public Application application;
    public RoomDatabase database;
    private UserDao userDao;
    private AccountDao accountDao;

    //Google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

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

    public String FuncGoogle (GoogleSignInAccount acct){
        String personName = "";
        String personEmail = "";
        //gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //gsc = GoogleSignIn.getClient(LoginFragment.newInstance().getActivity(), gso);
        //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LoginFragment.newInstance().getContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
        }
    return (personName+ " " + personEmail);
    }

    public int idAccountByEmail(String email){
        return accountDao.getId(email);
    }

}
