package com.example.clothual.UI.core.Personal;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.clothual.Database.AccountDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Database.UserDao;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class PersonalModel {

    public Application application;
    public RoomDatabase database;
    private final UserDao userDao;
    private final AccountDao accountDao;

    public PersonalModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        userDao = database.daoUser();
        accountDao = database.daoAccount();
    }

    public String getName(String username){
        List<Account> account = accountDao.getAllAccount();
        List<User> user = userDao.getAllUser();

        for(int i = 0; i <account.size(); i++){
            if(account.get(i).getUsername().equals(username)){
                int id = account.get(i).getId();
                for(int j = 0; j < user.size(); j++){
                    if(user.get(j).getIdAccount() == id){
                        return user.get(j).getName() + " " + user.get(j).getSurname();
                    }
                }
            }
        }
        return null;
    }

    public Bitmap importImageFromMemory(Activity act, Context ctx, ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            System.out.println("Permessi negati");
            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            System.out.println("Permessi concessi");
        }
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

}
