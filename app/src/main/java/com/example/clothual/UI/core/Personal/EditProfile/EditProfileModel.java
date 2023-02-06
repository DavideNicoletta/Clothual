package com.example.clothual.UI.core.Personal.EditProfile;

import static com.example.clothual.Util.Constant.PATTERN_DATE_FORMAT;
import static com.example.clothual.Util.Constant.PATTERN_HOUR_FORMAT;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.example.clothual.Database.AccountDao;
import com.example.clothual.Database.ClothualDao;
import com.example.clothual.Database.ImageDao;
import com.example.clothual.Database.RoomDatabase;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.Image;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EditProfileModel {

    public Application application;
    public RoomDatabase database;
    private final AccountDao accountDao;
    public final ImageDao imageDao;
    public final ClothualDao clothualDao;

    public EditProfileModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
        accountDao = database.daoAccount();
        imageDao = database.imageDao();
        clothualDao = database.clothualDao();
    }

    public boolean checkPassword(String password, int id){
        List<Account> account = accountDao.getAllAccount();
        for(int i = 0; i < account.size(); i++){
            if(account.get(i).getId() == id && account.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }

    public String getEmail(int id ){
        return accountDao.getEmail(id);
    }

    public String getUsername(int id ){
        return accountDao.getUsername(id);
    }


    public Uri saveImage(ContentResolver contentResolver, Bitmap image, String title, String description, int ID) throws IOException {
        return saveImageToMemory( contentResolver,  image,  title,  description, ID);
    }

    public Uri saveImageToMemory(ContentResolver contentResolver, Bitmap bitmap, String title, String description, int ID) throws IOException {
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
        Image image = new Image(title, description, uri.toString(), ID);
        imageDao.insertImage(image);
        return uri;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getNameImage(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(PATTERN_DATE_FORMAT)
                .withZone(ZoneId.systemDefault());
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(PATTERN_HOUR_FORMAT).withZone(ZoneId.systemDefault());
        Clock clock = Clock.systemDefaultZone();
        Instant instant = clock.instant();
        System.out.println("Nome stampato");
        return formatterDate.format(instant)+"__"+timeColonFormatter.format(instant);
    }

    public void createImage(String title, String description, String uri, int ID){
        Image image = new Image(title, description, uri, ID);
        imageDao.insertImage(image);
    }

    public Bitmap importImageFromMemory(Activity act, Context ctx, ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

    public Account getAccountByID(int id){
        return accountDao.getAccountID(id);
    }

    public void upoloadEditAccount(Account account){
        accountDao.updateAccount(account);
    }


}
