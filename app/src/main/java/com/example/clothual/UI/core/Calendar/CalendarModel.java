package com.example.clothual.UI.core.Calendar;

import android.app.Application;

import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Repository.Core.CoreRepository;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Converters;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;

import java.util.ArrayList;
import java.util.List;

public class CalendarModel {


    public Application application;
    public RoomDatabase database;
    public CoreRepository coreRepository;
    /*private final ImageDao imageDao;
    private final ClothualDao clothualDao;
    private final OutfitDao outfitDao;

     */



    public CalendarModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        coreRepository = new CoreRepository(application);
       /* imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();

        */
    }

    public List<Image> getImageList(int ID){
        List<Image> image = coreRepository.getAllImage();//imageDao.getAllImage();
        for(int i = 0; i < image.size(); i++){
            if(image.get(i).getDescription().equals("profile") || image.get(i).getIdAccount() != ID){
                image.remove(i);
            }
        }
        return image;
    }

    public List<Clothual> getClothualList(int ID){
        List<Clothual> clothualList = coreRepository.getAllClothual();
        for(int i = 0; i < clothualList.size(); i++){
            if(clothualList.get(i).getIdUser() != ID){
                clothualList.remove(i);
            }
        }
        return clothualList;
    }

    public void insertOutfit(Outfit outfit){
        coreRepository.insertOutfit(outfit);
       //outfitDao.insertOutfit(outfit);
    }

    public void updateOutfit(Outfit outfit){
        coreRepository.updateOutfit(outfit);
       // outfitDao.updateOutfit(outfit);
    }

    public Clothual getClothualByID(int id){
        return coreRepository.getClothualByID(id);
//        return clothualDao.getClothualByID(id);
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
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
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
        List<Outfit> outfit = coreRepository.getAllOutfit();//outfitDao.getAlLOutfit();
        for(int i = 0; i < outfit.size();  i++){
            if(outfit.get(i).getClothualString().equals("")){
                coreRepository.deliteOutfit(outfit.get(i));
                //outfitDao.deliteOutfit(outfit.get(i));
            }
        }
    }

    public Outfit getOutfitByDate(String date){
        return coreRepository.getOutfitByDate(date);
       // return outfitDao.getOutfitByDate(date);
    }

    public List<Clothual> getClothualOutfitDate(Outfit outfit, int ID){
        List<Clothual> clothualList = getClothualList(ID);
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
