package com.example.clothual.UI.core.Categories;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.R;
import com.example.clothual.UI.core.Photo.ShowPhoto;
import com.example.clothual.databinding.ActivityClothualElementShowBinding;

import java.io.FileNotFoundException;

public class ClothualElementShow extends AppCompatActivity {

    private ActivityClothualElementShowBinding binding;

    private CategoryModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClothualElementShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new CategoryModel(getApplication());

        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                binding.toolbarImage.setImageResource(R.drawable.logo_white_on_appbar);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                binding.toolbarImage.setImageResource(R.drawable.logo_black_on_white);
                break;
        }

        String uri = getIntent().getStringExtra("uri");
        try {
            binding.imageViewDress.setImageBitmap(model.importImageFromMemory(Uri.parse(uri)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        switch(getIntent().getIntExtra("type", 0)){
            case 1:
                binding.spinner.setText(getString(R.string.typeClothual) + ": " + getString(R.string.shoes));
                break;
            case 2:
                binding.spinner.setText(getString(R.string.typeClothual) + ": " + getString(R.string.trousers));
                break;
            case 3:
                    binding.spinner.setText(getString(R.string.typeClothual) + ": " + getString(R.string.tshirt));
                    break;
            case 4:
                binding.spinner.setText(getString(R.string.typeClothual) + ": " + getString(R.string.sweatshirt));
                break;
            case 5:
                binding.spinner.setText(getString(R.string.typeClothual) + ": " + getString(R.string.jeans));
                break;
        }

        binding.textViewBrand.setText(getString(R.string.brand) + ": " + getIntent().getStringExtra("brand"));
        binding.textViewTemplate.setText(getString(R.string.template) + ": " + getIntent().getStringExtra("template"));
        binding.textViewColor.setText(getString(R.string.color) + ": " + getIntent().getStringExtra("color"));
        binding.textViewDescriprion.setText(getString(R.string.description) + ": " + getIntent().getStringExtra("description"));

        binding.imageViewDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClothualElementShow.this, ShowPhoto.class);
                intent.putExtra("uri", getIntent().getStringExtra("uri"));
                startActivity(intent);
            }
        });

    }
}