package com.example.clothual.UI.core.Categories;

import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Repository.Core.CoreRepository;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Converters;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    public Application application;
    public RoomDatabase database;
    public ContentResolver contentResolver;
    public CoreRepository coreRepository;
   /* private ImageDao imageDao;
    private ClothualDao clothualDao;

    private OutfitDao outfitDao;

    */

    public CategoryModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
       /* imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();

        */
        contentResolver = application.getApplicationContext().getContentResolver();
        coreRepository = new CoreRepository(application);
    }

    public List<Image> getImageList(int ID){
        List<Image> listImage = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> returnList = new ArrayList<>();
        for( int i = 0; i < listImage.size(); i++){
            if(listImage.get(i).getIdAccount() == ID) {
                returnList.add(listImage.get(i));
            }
        }

        return returnList;
    }

    public List<Image> getImageShoesList(List<Clothual> clothualList, int ID) {
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> shoes = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID() && clothualList.get(i).getIdUser() == ID){
                    shoes.add(imageList.get(j));
                }
            }
        }
        return shoes;
    }

    public List<Image> getImagePreferiteList(List<Clothual> clothualList, int ID) {
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> preferite = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID() && clothualList.get(i).getIdUser() == ID){
                    preferite.add(imageList.get(j));
                }
            }
        }
        return preferite;
    }

   public List<Clothual> getShoesList(int ID){
        List<Clothual> list = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<Clothual> shoes = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 1 && list.get(i).getIdUser() == ID){
                shoes.add(list.get(i));
            }
        }

       return shoes;

    }

    public List<Clothual> getTrousersList(int ID){
        List<Clothual> list = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<Clothual> trousers = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 2 && list.get(i).getIdUser() == ID){
                trousers.add(list.get(i));
            }
        }
        return trousers;

    }

    public List<Image> getImageTrousersList(List<Clothual> clothualList, int ID) {
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> trousers = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID() && clothualList.get(i).getIdUser() == ID){
                    trousers.add(imageList.get(j));
                }
            }
        }
        return trousers;
    }

    public List<Clothual> getJacketsList(int ID){
        List<Clothual> list = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<Clothual> jackets = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 4 && list.get(i).getIdUser() == ID){
                jackets.add(list.get(i));
            }
        }
        return jackets;

    }

    public List<Image> getImageJacketsList(List<Clothual> clothualList, int ID) {
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> jackets = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID() && clothualList.get(i).getIdUser() == ID){
                    jackets.add(imageList.get(j));
                }
            }
        }
        return jackets;
    }

    public List<Clothual> getTShirtList(int ID){
        List<Clothual> list = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<Clothual> tShirt = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 3 && list.get(i).getIdUser() == ID){
                tShirt.add(list.get(i));
            }
        }
        return tShirt;

    }

    public List<Image> getImageTShirtList(List<Clothual> clothualList, int ID) {
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> tShirt = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID() && clothualList.get(i).getIdUser() == ID){
                    tShirt.add(imageList.get(j));
                }
            }
        }
        return tShirt;
    }

    public List<Clothual> getJeansList(int ID){
        List<Clothual> list = coreRepository.getAllClothual();//o.getAllClothual();
        List<Clothual> jeans = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getType() == 5 && list.get(i).getIdUser() == ID){
                jeans.add(list.get(i));
            }
        }
        return jeans;

    }

    public List<Image> getImageJeansList(List<Clothual> clothualList, int ID) {
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> jeans = new ArrayList<>();
        for(int i = 0; i < clothualList.size(); i++){
            for(int j = 0; j < imageList.size(); j++){
                if(clothualList.get(i).getIdImage() == imageList.get(j).getID() && clothualList.get(i).getIdUser() == ID){
                    jeans.add(imageList.get(j));
                }
            }
        }
        return jeans;
    }


    public List<Clothual> getClothualList(int ID){
        List<Clothual> listClothual = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<Clothual> returnList = new ArrayList<>();
        for( int i = 0; i < listClothual.size(); i++){
            if(listClothual.get(i).getIdUser() == ID) {
                returnList.add(listClothual.get(i));
            }
        }
        return returnList;
    }

    public List<Clothual> getPreferiteList(int ID){
        List<Clothual> list = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<Clothual> preferite = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).isPreferite() && list.get(i).getIdUser() == ID){
                preferite.add(list.get(i));
            }
        }
        return preferite;
    }

    public void deleteClothual(Clothual clothual){
        coreRepository.deleteClothual(clothual);
        //clothualDao.deleteClothual(clothual);
    }

    public void deleteImage(Image image){
        coreRepository.deleteImage(image);
        //imageDao.deleteImage(image);
    }

    public void updateClothaulElement(Clothual clothual){
        coreRepository.updateClothual(clothual);
        //clothualDao.updateClothual(clothual);
    }

    public void deleteElement(ContentResolver contentResolver, Uri uri, int ID) throws FileNotFoundException {
        deleteFromOutfit(ID);
        contentResolver.delete (uri,null ,null );
    }

    public void deleteFromOutfit(int ID){
        List<Outfit> outfitList = coreRepository.getAllOutfit();//outfitDao.getAlLOutfit();
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
                coreRepository.updateOutfit(outfit);
                //outfitDao.updateOutfit(outfit);

            }
        }
    }


    public List<Clothual> getClothualOutfit(Outfit outfit){
        List<Clothual> clothualList = coreRepository.getAllClothual();//clothualDao.getAllClothual();
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


    //Show element
    public Bitmap importImageFromMemory(Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

}

