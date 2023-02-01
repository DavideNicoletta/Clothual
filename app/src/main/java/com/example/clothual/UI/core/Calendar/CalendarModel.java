package com.example.clothual.UI.core.Calendar;

import android.app.Application;

import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.ImageDao;
import com.example.clothual.Database.OutfitDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Converters;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;

import java.util.ArrayList;
import java.util.List;

public class CalendarModel {


    public Application application;
    public RoomDatabase database;
    private final ImageDao imageDao;
    private final ClothualDao clothualDao;
    private final OutfitDao outfitDao;

    public CalendarModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();
    }

    public List<Image> getImageList(){
        return imageDao.getAllImage();
    }

    public List<Clothual> getClothualList(){
        return clothualDao.getAllClothual();
    }

    public void insertOutfit(Outfit outfit){
        outfitDao.insertOutfit(outfit);
    }

    public void updateOutfit(Outfit outfit){
        outfitDao.updateOutfit(outfit);
    }

    public Clothual getClothualByID(int id){
        return clothualDao.getClothualByID(id);
    }

    public List<Clothual> getClothualOutfit(String date){
        Outfit outfitElement = getOutfitByDate(date);
        List<Clothual> get = new ArrayList<>();
        if(outfitElement != null){
            List<String> listIdClothual = Converters.fromString(outfitElement.getClothualString());
            for (int j = 0; j < listIdClothual.size(); j++) {
                get.add(getClothualByID(Integer.parseInt(listIdClothual.get(j))));
            }
            return get;
        }else{
            return null;
        }
    }

    public List<Image> getImageOutfit(List<Clothual> clothualList) {
        List<Image> imageList = imageDao.getAllImage();
        List<Image> outfit = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID()){
                    outfit.add(imageList.get(j));
                }
            }
        }
        return outfit;
    }

    public void checkOutfitList(){
        List<Outfit> outfit = outfitDao.getAlLOutfit();
        for(int i = 0; i < outfit.size();  i++){
            if(outfit.get(i).getClothualString().equals("")){
                outfitDao.deliteOutfit(outfit.get(i));
            }
        }
    }

    public Outfit getOutfitByDate(String date){
        return outfitDao.getOutfitByDate(date);
    }

    public List<Clothual> getClothualOutfitDate(Outfit outfit){
        List<Clothual> clothualList = getClothualList();
        List<String> listIdClothual = Converters.fromString(outfit.getClothualString());
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < listIdClothual.size(); j++){
                if(clothualList.get(i).getId() == Integer.parseInt(listIdClothual.get(j))){
                    clothualList.remove(i);
                }
            }
        }
        return clothualList;
    }


}
