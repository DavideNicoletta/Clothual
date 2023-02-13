package com.example.clothual.UI.core.Photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.databinding.ActivityShowPhotoBinding;

import java.io.FileNotFoundException;

public class ShowPhoto extends AppCompatActivity {

    private ActivityShowPhotoBinding binding;

    private ShowPhotoModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ShowPhotoModel(getContentResolver());

        try {
            binding.imageView.setImageBitmap(model.importImageFromMemory(Uri.parse(getIntent().getStringExtra("uri"))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }
}