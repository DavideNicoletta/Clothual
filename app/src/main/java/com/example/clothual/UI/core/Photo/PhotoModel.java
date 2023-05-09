package com.example.clothual.UI.core.Photo;

import static com.example.clothual.Util.Constant.PATTERN_DATE_FORMAT;
import static com.example.clothual.Util.Constant.PATTERN_HOUR_FORMAT;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.clothual.Data.Database.RoomDatabase;
import com.example.clothual.Data.Repository.Core.CoreRepository;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Converters;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PhotoModel {

    public Application application;
    public RoomDatabase database;
    //private final ImageDao imageDao;

    public final ContentResolver contentResolver;
    //private final ClothualDao clothualDao;

    //public final OutfitDao outfitDao;

    public CoreRepository coreRepository;

    public PhotoModel(Application application) {
        this.application = application;
        contentResolver = application.getContentResolver();
        database = RoomDatabase.getDatabase(application);
       // imageDao = database.imageDao();
       // clothualDao = database.clothualDao();
       // outfitDao = database.outfitDao();
        coreRepository = new CoreRepository(application);
    }



    public Uri saveImage(ContentResolver contentResolver, Bitmap image, String title, String description, int id) throws IOException {
        return saveImageToMemory( contentResolver,  image,  title,  description, id);
    }


    public Uri saveImageToMemory(ContentResolver contentResolver, Bitmap bitmap, String title, String description, int id) throws IOException {
        System.out.println("Salvataggio");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        OutputStream outputStream = contentResolver.openOutputStream(uri);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();

        Image image = new Image(title, description, uri.toString(), id);
        //imageDao.insertImage(image);
        coreRepository.insertImage(image);
        return uri;
    }


    public List<Image> getImageList(int ID){
        List<Image> imageList = coreRepository.getAllImage();//imageDao.getAllImage();
        List<Image> getList = new ArrayList<>();
        for(int i = 0; i < imageList.size(); i++){
            if(!imageList.get(i).getDescription().equals("profile")){
               getList.add(imageList.get(i));
            }
        }


        for(int i = 0; i < getList.size(); i++){
            if(getList.get(i).getIdAccount() != ID){
                System.out.println(imageList.get(i).getIdAccount());
                getList.remove(getList.get(i));
            }
        }

        if(getList.size() == 1 && getList.get(0).getIdAccount() != ID){
            return null;
        }

        return getList;
    }



    public Bitmap importImageFromMemory(Activity act, Context ctx, ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getNameImage(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(PATTERN_DATE_FORMAT)
                .withZone(ZoneId.systemDefault());
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(PATTERN_HOUR_FORMAT).withZone(ZoneId.systemDefault());
        Clock clock = Clock.systemDefaultZone();
        Instant instant = clock.instant();
        return formatterDate.format(instant)+"__"+timeColonFormatter.format(instant);
    }

    public void deliteImage(Image image){
        coreRepository.deleteImage(image);
        //imageDao.deleteImage(image);
    }

    public void deliteClothual(Clothual clothual){
        coreRepository.deleteClothual(clothual);
        //clothualDao.deleteClothual(clothual);
    }

    public List<Clothual> getAllClothual(){
        return coreRepository.getAllClothual();
       // return clothualDao.getAllClothual();
    }


    public void deleteElement(ContentResolver contentResolver, Uri uri, int ID){
        int index = takeIDClothualByIDImage(ID);
        deleteFromOutfit(index);
        contentResolver.delete (uri,null ,null );
    }

    public int takeIDClothualByIDImage(int idImage){
        List<Clothual> clothualList = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        for(int i = 0; i < clothualList.size(); i++){
            if(clothualList.get(i).getIdImage() == idImage){
                return clothualList.get(i).getId();
            }
        }
        return -1;
    }


    public void deleteFromOutfit(int ID){
        List<Outfit> outfitList = coreRepository.getAllOutfit();//outfitDao.getAlLOutfit();
        if(outfitList.size() != 0){
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

    public Bitmap importImageFromMemoryShowPhoto(Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

}
