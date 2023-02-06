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

import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.ImageDao;
import com.example.clothual.Database.OutfitDao;
import com.example.clothual.Database.RoomDatabase;
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
    private final ImageDao imageDao;
    private final ClothualDao clothualDao;

    public final OutfitDao outfitDao;

    public PhotoModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
        outfitDao = database.outfitDao();
    }

    public Uri saveImage(ContentResolver contentResolver, Bitmap image, String title, String description) throws IOException {
        return saveImageToMemory( contentResolver,  image,  title,  description);
    }


    public Uri saveImageToMemory(ContentResolver contentResolver, Bitmap bitmap, String title, String description) throws IOException {
        // Crea una nuova entrata per l'immagine nella memoria del dispositivo
        System.out.println("Salvataggio");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Apri uno stream di output verso la nuova entrata
        OutputStream outputStream = contentResolver.openOutputStream(uri);

        // Salva l'immagine nella memoria del dispositivo
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();

        // Restituisce l'URI dell'immagine appena salvata
        Image image = new Image(title, description, uri.toString());
        imageDao.insertImage(image);
        return uri;
    }


    public List<Image> getImageList(){//Activity act, Context ctx, ContentResolver contentResolver) {
        List<Image> image = imageDao.getAllImage();
        for(int i = 0; i < image.size(); i++){
            if(image.get(i).getDescription().equals("profile")){
                image.remove(i);
            }
        }
        return image;

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
        imageDao.deleteImage(image);
    }

    public void deliteClothual(Clothual clothual){
        clothualDao.deleteClothual(clothual);
    }

    public List<Clothual> getAllClothual(){
        return clothualDao.getAllClothual();
    }


    public void deleteElement(ContentResolver contentResolver, Uri uri, int ID){
        int index = takeIDClothualByIDImage(ID);
        deleteFromOutfit(index);
        contentResolver.delete (uri,null ,null );
    }

    public int takeIDClothualByIDImage(int idImage){
        List<Clothual> clothualList = clothualDao.getAllClothual();
        for(int i = 0; i < clothualList.size(); i++){
            if(clothualList.get(i).getIdImage() == idImage){
                return clothualList.get(i).getId();
            }
        }
        return -1;
    }

    public String getNamePhotoByID(int ID){
        List<Image> imageList = imageDao.getAllImage();
        for(int i = 0; i < imageList.size(); i++){
            if(imageList.get(i).getID() == ID){
                return imageList.get(i).getTitle();
            }
        }
        return null;
    }

    public void deleteFromOutfit(int ID){
        List<Outfit> outfitList = outfitDao.getAlLOutfit();
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
