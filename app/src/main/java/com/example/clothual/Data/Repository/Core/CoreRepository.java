package com.example.clothual.Data.Repository.Core;

import android.app.Application;

import com.example.clothual.Data.Database.AccountDao;
import com.example.clothual.Data.Database.ClothualDao;
import com.example.clothual.Data.Database.ImageDao;
import com.example.clothual.Data.Database.OutfitDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;
import com.example.clothual.Model.User;

import java.util.List;

public class CoreRepository {

    public Application application;
    public RoomDatabase database;
    private final ImageDao imageDao;
    private final ClothualDao clothualDao;
    public final OutfitDao outfitDao;
    public final AccountDao accountDao;
    public final UserDao userDao;

    public CoreRepository(Application application){
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();
        accountDao = database.daoAccount();
        userDao = database.daoUser();
    }

    //ImageDao

    public void insertImage(Image image){
        imageDao.insertImage(image);
    }

    public void deleteImage(Image image){
        imageDao.deleteImage(image);
    }

    public List<Image> getAllImage(){
        return imageDao.getAllImage();
    }

    public int getIdByUri(String uri){
        return imageDao.getIDByUri(uri);
    }


    //ClothualDao

    public void deleteClothual(Clothual clothual){
        clothualDao.deleteClothual(clothual);
    }

    public List<Clothual> getAllClothual(){
        return clothualDao.getAllClothual();
    }

    public void updateClothual(Clothual clothual){
        clothualDao.updateClothual(clothual);
    }

    public Clothual getClothualByID(int id){
        return clothualDao.getClothualByID(id);
    }

    public void insertClothual(Clothual clothual){
        clothualDao.insertClothual(clothual);
    }

    //OutfitDao

    public List<Outfit> getAllOutfit(){
        return outfitDao.getAlLOutfit();
    }

    public void updateOutfit(Outfit outfit){
        outfitDao.updateOutfit(outfit);
    }

    public void insertOutfit(Outfit outfit){
        outfitDao.insertOutfit(outfit);
    }

    public void deliteOutfit(Outfit outfit){
        outfitDao.deliteOutfit(outfit);
    }

    public Outfit getOutfitByDate(String date){
        return outfitDao.getOutfitByDate(date);
    }

    //AccountDao

    public List<Account> getAllAccount(){
        return accountDao.getAllAccount();
    }

    public String getEmail(int id){
        return accountDao.getEmail(id);
    }

    public String getUsername(int id){
        return accountDao.getUsername(id);
    }

    public Account getAccountID(int id){
        return  accountDao.getAccountID(id);
    }

    public void updateAccount(Account account){
        accountDao.updateAccount(account);
    }

    //UserDao

    public List<User> getAllUser(){
        return userDao.getAllUser();
    }

}
