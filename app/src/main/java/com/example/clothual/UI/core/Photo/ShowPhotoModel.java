package com.example.clothual.UI.core.Photo;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ShowPhotoModel {

    private ContentResolver contentResolver;

    public ShowPhotoModel(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    public Bitmap importImageFromMemory(Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }


}
