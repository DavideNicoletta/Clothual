package com.example.clothual.Data.Repository.Welcome;

import android.app.Application;

import com.example.clothual.Data.Database.AccountDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;
import com.example.clothual.Data.Source.User.UserAuthenticationRemoteDataSource;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

public class AuthenticationRepository{

    public Application application;

    public RoomDatabase database;
    private final UserDao userDao;
    private final AccountDao accountDao;

    private UserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource;

    //private BaseUserAuthenticationRemoteDataSource userRemoteDataSource;

    public AuthenticationRepository(Application application){
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
        this.userAuthenticationRemoteDataSource = new UserAuthenticationRemoteDataSource();
        //this.userRemoteDataSource = userRemoteDataSource;
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

    public int getIDByEmail(String email){
        return accountDao.getIdByEmail(email);
    }

    public Account getAccountByUerName(String username){
        return accountDao.getAccountByUerName(username);
    }

    public void signUp(String email, String password, String surname, String name, String username) {
        userAuthenticationRemoteDataSource.signUp(email, password);
        createUser(username, name, surname, password, email);
    }

    public void createUser(String username, String name, String surname, String passowrd, String email){
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            Account account = new Account(username, email, passowrd);
            insertAccount(account);
            //accountDao.insertAccount(account);
            User user = new User(surname, name, getId(username));
            insertUser(user);
            //userDao.insertUser(user);
        });

    }

    /*
    @Override
    public void signUp(String email, String password) {
        userRemoteDataSource.signUp(email, password);
    }

    @Override
    public void signIn(String email, String password) {
        userRemoteDataSource.signIn(email, password);
    }

    @Override
    public void signInWithGoogle(String token) {
        userRemoteDataSource.signInWithGoogle(token);

    }
*/
}
