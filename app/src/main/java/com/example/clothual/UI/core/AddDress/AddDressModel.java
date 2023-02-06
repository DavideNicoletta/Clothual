package com.example.clothual.UI.core.AddDress;

import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.ImageDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AddDressModel {


    public Application application;
    public RoomDatabase database;
    private ImageDao imageDao;
    private ClothualDao clothualDao;

    public AddDressModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
    }



    public Bitmap importImageFromMemory(ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

    public int getIdByUri(String uri){
        return imageDao.getIDByUri(uri);
    }

    public void createClothual(int type, String brand, String description, String color, String template, int idImage, int id){
        Clothual clothual = new Clothual(type, brand, description, color, template, idImage, id);
        clothualDao.insertClothual(clothual);
    }

    public Clothual getClothualByID(int id){
        return clothualDao.getClothualByID(id);
    }

    public void update(Clothual clothual){
        clothualDao.updateClothual(clothual);
    }

    public List<Image> getAllImage(){
        return imageDao.getAllImage();
    }

    public void deliteImage(Image image){
        imageDao.deleteImage(image);
    }

}
