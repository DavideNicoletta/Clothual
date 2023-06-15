package com.example.clothual.Data.Repository.Core;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.example.clothual.Data.Database.ClothualDao;
import com.example.clothual.Data.Database.ImageDao;
import com.example.clothual.Data.Database.OutfitDao;
import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Database.UserDao;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;
import com.example.clothual.Model.User;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

public class CoreRepository {

    public Application application;
    public RoomDatabase database;
    private final ImageDao imageDao;
    private final ClothualDao clothualDao;
    public final OutfitDao outfitDao;

    public final UserDao userDao;

    public CoreRepository(Application application){
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();

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

    public String getEmail(String id){
        return userDao.getEmail(id);
    }

    public String getUsername(String id){
        return userDao.getUsername(id);
    }

    public User getUserByID(String id){
        return  userDao.getUserByUid(id);
    }

    public void updateUser(User user){
        userDao.updateUser(user);
    }

    //UserDao

    public List<User> getAllUser(){
        return userDao.getAllUser();
    }

    public GpsMyLocationProvider getProvider(Context ctx){
        GpsMyLocationProvider provider = new GpsMyLocationProvider(ctx);
        provider.addLocationSource(LocationManager.NETWORK_PROVIDER);
        return provider;
    }

    public MyLocationNewOverlay getLocationOverlay(Context ctx, MapView mapView){
        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(getProvider(ctx), mapView);
        locationOverlay.enableFollowLocation();
        locationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                Log.d("MyTag", String.format("First location fix: %s", locationOverlay.getLastFix()));
            }
        });
        return locationOverlay;
    }

}
