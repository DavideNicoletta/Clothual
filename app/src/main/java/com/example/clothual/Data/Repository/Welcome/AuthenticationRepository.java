package com.example.clothual.Data.Repository.Welcome;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.clothual.Data.Database.AccountDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;
import com.example.clothual.Data.Source.User.UserAuthenticationRemoteDataSource;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;
import com.example.clothual.Util.SharePreferenceReadWrite;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthenticationRepository{

    public Application application;

    public RoomDatabase database;
    private final UserDao userDao;
    private final AccountDao accountDao;

    FirebaseFirestore firebaseFirestore;
    private final FirebaseUser firebaseUser;
    private final FirebaseAuth firebaseAuth;

    private SharePreferenceReadWrite sharePreferenceReadWrite;
    private UserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource;


    public AuthenticationRepository(Application application){
        this.application = application;
        this.database = RoomDatabase.getDatabase(application);
        this.userDao = database.daoUser();
        this.accountDao = database.daoAccount();
        this.userAuthenticationRemoteDataSource = new UserAuthenticationRemoteDataSource();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = firebaseAuth.getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        sharePreferenceReadWrite = new SharePreferenceReadWrite(application);

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
       // createUserRepository(username, name, surname, password, email);
    }

    public void createUserRepository(String username, String name, String surname, String passowrd, String email, String id){
        System.out.println("in create");
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            Account account = new Account(username, email, passowrd);
            insertAccount(account);
            System.out.println("Account create");
            User user = new User(surname, name, getId(username), id);
            insertUser(user);
            System.out.println("User create");
        });

    }

    public User createReturnUserRepository(String username, String name, String surname, String password, String email, String id){
        System.out.println("in create");
        Account account = new Account(username, email, password);
        User user = new User(surname, name, getId(username), id);
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            insertAccount(account);
            insertUser(user);
        });
        return user;
    }

    public boolean firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                       if(getUserByUid(account.getId()) == null) {
                           String email = account.getEmail();
                           String surname = account.getFamilyName();
                           String name = account.getGivenName();
                           createUserRepository("google", name, surname, "google", email, account.getId());
                           firebaseFirestore.collection("User")
                                   .document(FirebaseAuth.getInstance().getUid())
                                   .set(createReturnUserRepository("google", name, surname, "google",
                                           email, account.getId()));
                       }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return true;
    }


    public boolean handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            return true;
        }
        return false;
    }

    public User getUserByUid(String Uid){
         return userDao.getUserByUid(Uid);
    }
}
