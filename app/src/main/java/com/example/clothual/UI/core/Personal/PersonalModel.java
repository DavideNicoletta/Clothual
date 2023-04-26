package com.example.clothual.UI.core.Personal;

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
import com.example.clothual.Data.Repository.CoreRepository;
import com.example.clothual.Model.Account;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.User;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalModel {

    public Application application;
    public RoomDatabase database;
   /* private final UserDao userDao;
    private final AccountDao accountDao;

    private final ImageDao imageDao;
    private final ClothualDao clothualDao;

    */

    public CoreRepository coreRepository;

    public PersonalModel(Application application) {
        this.application = application;
        database = RoomDatabase.getDatabase(application);
       /* userDao = database.daoUser();
        accountDao = database.daoAccount();
        clothualDao = database.clothualDao();
        imageDao = database.imageDao();
        */
        coreRepository = new CoreRepository(application);
    }

    public String getName(String username){
        List<Account> account = coreRepository.getAllAccount();//accountDao.getAllAccount();
        List<User> user = coreRepository.getAllUser();//userDao.getAllUser();

        for(int i = 0; i <account.size(); i++){
            if(account.get(i).getUsername().equals(username)){
                int id = account.get(i).getId();
                for(int j = 0; j < user.size(); j++){
                    if(user.get(j).getIdAccount() == id){
                        return user.get(j).getName() + " " + user.get(j).getSurname();
                    }
                }
            }
        }
        return null;
    }

    public Bitmap importImageFromMemory(Activity act, Context ctx, ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            System.out.println("Permessi negati");
            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            System.out.println("Permessi concessi");
        }
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }


    //History
    public float[] getRateTypeClothual(){
        List<Clothual> clothualList = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        float [] rate = {0, 0, 0, 0, 0};
        for(int i = 0; i < clothualList.size(); i++){
            switch (clothualList.get(i).getType()){
                case 1:
                    rate[0] = rate[0]+1;
                    break;
                case 2:
                    rate[1] = rate[1]+1;
                    break;
                case 3:
                    rate[2] = rate[2]+1;
                    break;
                case 4:
                    rate[3] = rate[3]+1;
                    break;
                case 5:
                    rate[4] = rate[4]+1;
            }

        }
        System.out.println("rate1: " + rate[0] + ", Rate2: " + rate[1]
                + ", Rate3: " + rate[2] + ", Rate4: " + rate[3] + ", Rate5: " + rate[4]);
        for(int i = 0; i < rate.length; i++){
            rate[i] = (rate[i]/clothualList.size()) * 100;
        }
        return rate;
    }

    public List<String> mapToList(HashMap<String, Integer> rate, List<String> result, int size){
        float max = (Collections.max(rate.values()));
        for (Map.Entry<String, Integer> entry : rate.entrySet()) {
            if (entry.getValue() == max) {
                result.add(entry.getKey());
                result.add("" + (int) ((max / size) * 100));
                result.add(entry.getKey());
                return result;
            }
        }
        return null;
    }

    public List<String> getRateColor(){
        List<Clothual> clothualList = coreRepository.getAllClothual();//clothualDao.getAllClothual();
        List<String> result = new ArrayList<>();
        HashMap<String, Integer> rate = new HashMap<String, Integer>();
        for(int i = 0; i < clothualList.size(); i++){
            if(rate.containsKey(clothualList.get(i).getColor())){
                Integer interim = rate.get(clothualList.get(i).getColor()) +1 ;
                rate.put(clothualList.get(i).getColor(), interim);
            }else{
                rate.put(clothualList.get(i).getColor(), 1);
            }
        }

        for(int i = 0; i < 3; i++){
            if(!rate.isEmpty()){
                result = mapToList(rate, result, clothualList.size());
                rate.remove(result.get(result.size()-1));
                result.remove(result.size()-1);
            }else{
                return  result;
            }
        }
        return  result;
    }


    //Edit Profile
    public boolean checkPassword(String password, int id){
        List<Account> account = coreRepository.getAllAccount();//accountDao.getAllAccount();
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
        return coreRepository.getEmail(id);//accountDao.getEmail(id);
    }

    public String getUsername(int id ){
        return coreRepository.getUsername(id);//accountDao.getUsername(id);
    }


    public Uri saveImage(ContentResolver contentResolver, Bitmap image, String title, String description, int ID) throws IOException {
        return saveImageToMemory( contentResolver,  image,  title,  description, ID);
    }

    public Uri saveImageToMemory(ContentResolver contentResolver, Bitmap bitmap, String title, String description, int ID) throws IOException {
        System.out.println("Salvataggio");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        OutputStream outputStream = contentResolver.openOutputStream(uri);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();

        Image image = new Image(title, description, uri.toString(), ID);
        coreRepository.insertImage(image);//imageDao.insertImage(image);
        return uri;
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

    public void createImage(String title, String description, String uri, int ID){
        Image image = new Image(title, description, uri, ID);
        coreRepository.insertImage(image);//imageDao.insertImage(image);
    }

    public Bitmap importImageFromMemoryEditProfile(Activity act, Context ctx, ContentResolver contentResolver, Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }

    public Account getAccountByID(int id){
        return coreRepository.getAccountID(id);//accountDao.getAccountID(id);
    }

    public void upoloadEditAccount(Account account){
        coreRepository.updateAccount(account);//accountDao.updateAccount(account);
    }


}
