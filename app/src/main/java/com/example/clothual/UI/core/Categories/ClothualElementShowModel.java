package com.example.clothual.UI.core.Categories;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClothualElementShowModel {

    private ContentResolver contentResolver;

    public ClothualElementShowModel(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    public Bitmap importImageFromMemory(Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return BitmapFactory.decodeStream(inputStream);
    }
}
