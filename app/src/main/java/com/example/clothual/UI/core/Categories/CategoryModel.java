package com.example.clothual.UI.core.Categories;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;

import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.ImageDao;
import com.example.clothual.Database.OutfitDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Converters;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    public Application application;
    public RoomDatabase database;
    private ImageDao imageDao;
    private ClothualDao clothualDao;

    private OutfitDao outfitDao;

    public CategoryModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();
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

    public void deleteElement(ContentResolver contentResolver, Uri uri, int ID) throws FileNotFoundException {
        deleteFromOutfit(ID);
        contentResolver.delete (uri,null ,null );
    }

    public void deleteFromOutfit(int ID){
        List<Outfit> outfitList = outfitDao.getAlLOutfit();
        if(outfitList.size()!=0) {
            for (int i = 0; i < outfitList.size(); i++) {
                Outfit outfit = outfitList.get(i);
                List<Clothual> clothualList = getClothualOutfit(outfit);

                for (int j = 0; j < clothualList.size(); j++) {
                    if (clothualList.get(j).getId() == ID) {
                        clothualList.remove(j);

                    }
                }

                outfit.setClothualListByList(clothualList);
                outfit.converter();
                outfit.removeClothualList();
                outfitDao.updateOutfit(outfit);

            }
        }
    }


    public List<Clothual> getClothualOutfit(Outfit outfit){
        List<Clothual> clothualList = clothualDao.getAllClothual();
        List<String> listIdClothual = Converters.fromString(outfit.getClothualString());
        List<Clothual> clothualOutfit = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < listIdClothual.size(); j++){
                if(clothualList.get(i).getId() == Integer.parseInt(listIdClothual.get(j))){
                    clothualOutfit.add(clothualList.get(i));
                }
            }
        }
        return clothualOutfit;
    }
}

