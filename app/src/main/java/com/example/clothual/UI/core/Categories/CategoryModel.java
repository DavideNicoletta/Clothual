package com.example.clothual.UI.core.Categories;

import android.app.Application;

import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.ImageDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    public Application application;
    public RoomDatabase database;
    private ImageDao imageDao;
    private ClothualDao clothualDao;

    public CategoryModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
    }

    public List<Image> getImageList(){
        return imageDao.getAllImage();
    }

    public List<Image> getImageShoesList(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> shoes = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    shoes.add(imageList.get(j));
                }
            }
        }
        return shoes;
    }

    public List<Image> getImagePreferiteList(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> preferite = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    preferite.add(imageList.get(j));
                }
            }
        }
        return preferite;
    }

   public List<Clothual> getShoesList(){
        List<Clothual> list = clothualDao.getAllClothual();
        List<Clothual> shoes = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 1){
                shoes.add(list.get(i));
            }
        }

       return shoes;

    }

    public List<Clothual> getTrousersList(){
        List<Clothual> list = clothualDao.getAllClothual();
        List<Clothual> trousers = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 2){
                trousers.add(list.get(i));
            }
        }
        return trousers;

    }

    public List<Image> getImageTrousersList(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> trousers = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    trousers.add(imageList.get(j));
                }
            }
        }
        return trousers;
    }

    public List<Clothual> getJacketsList(){
        List<Clothual> list = clothualDao.getAllClothual();
        List<Clothual> jackets = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 4){
                jackets.add(list.get(i));
            }
        }
        return jackets;

    }

    public List<Image> getImageJacketsList(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> jackets = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    jackets.add(imageList.get(j));
                }
            }
        }
        return jackets;
    }

    public List<Clothual> getTShirtList(){
        List<Clothual> list = clothualDao.getAllClothual();
        List<Clothual> tShirt = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 3){
                tShirt.add(list.get(i));
            }
        }
        return tShirt;

    }

    public List<Image> getImageTShirtList(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> tShirt = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    tShirt.add(imageList.get(j));
                }
            }
        }
        return tShirt;
    }

    public List<Clothual> getJeansList(){
        List<Clothual> list = clothualDao.getAllClothual();
        List<Clothual> jeans = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 5){
                jeans.add(list.get(i));
            }
        }
        return jeans;

    }

    public List<Image> getImageJeansList(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> jeans = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    jeans.add(imageList.get(j));
                }
            }
        }
        return jeans;
    }


    public List<Clothual> getClothualList(){
        return clothualDao.getAllClothual();
    }

    public List<Clothual> getPreferiteList(){
        List<Clothual> list = clothualDao.getAllClothual();
        List<Clothual> preferite = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).isPreferite()){
                preferite.add(list.get(i));
            }
        }
        System.out.println("Lughezza preferiti: " + preferite.size());
        return preferite;
    }

    public void deleteClothual(Clothual clothual){
        clothualDao.deleteClothual(clothual);
    }

    public void deleteImage(Image image){
        imageDao.deleteImage(image);
    }

    public void updateClothaulElement(Clothual clothual){
        clothualDao.updateClothual(clothual);
    }

}
