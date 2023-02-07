package com.example.clothual.UI.core.AddDress;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.R;
import com.example.clothual.UI.core.CoreActivity;

import java.io.FileNotFoundException;
import java.util.List;

public class AddDressActivity extends AppCompatActivity {

    public AddDressModel model;
    public ImageView imageViewDress;
    public Spinner spinner;
    public EditText brand;
    public EditText template;
    public EditText color;
    public EditText description;
    public Button buttonSave;

    public ContentResolver contentResolver;

    public ImageView clothual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dress_layout);
        SharedPreferences sharedPref = getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        contentResolver = getApplication().getContentResolver();

        clothual = findViewById(R.id.clothualAddDress);

        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                clothual.setImageResource(R.drawable.logo_white_on_appbar);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                clothual.setImageResource(R.drawable.logo_black_on_white);
                break;
        }

        model = new AddDressModel(getApplication());

        imageViewDress = findViewById(R.id.imageViewDress);
        spinner = findViewById(R.id.spinner);
        brand = findViewById(R.id.editTextBrand);
        template = findViewById(R.id.editTextTemplate);
        color = findViewById(R.id.editTextColor);
        description = findViewById(R.id.editTextDescriprion);
        buttonSave = findViewById(R.id.save);

        String uri = getIntent().getExtras().getString("uri");
        int action = getIntent().getExtras().getInt("action");
        int id = getIntent().getExtras().getInt("id");

        try {
            Bitmap image = model.importImageFromMemory(getContentResolver(), Uri.parse(uri));
            imageViewDress.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(action == 1){
            Clothual clothual = model.getClothualByID(id);
            brand.setText(clothual.getBrand());
            template.setText(clothual.getTemplate());
            color.setText(clothual.getColor());
            description.setText(clothual.getDescription());
        }

        buttonSave.setOnClickListener(view -> {
            int spinnerValue = 0;
            if(spinner.getSelectedItem().toString().equals(getString(R.string.shoes))){
                spinnerValue = 1;
            }
            if(spinner.getSelectedItem().toString().equals(getString(R.string.trousers))){
                spinnerValue = 2;
            }
            if(spinner.getSelectedItem().toString().equals(getString(R.string.tshirt))){
                spinnerValue = 3;
            }
            if(spinner.getSelectedItem().toString().equals(getString(R.string.sweatshirt))){
                spinnerValue = 4;
            }
            if(spinner.getSelectedItem().toString().equals(getString(R.string.jeans))){
                spinnerValue = 5;
            }


            switch(action){
                case 0:
                    model.createClothual(spinnerValue, brand.getText().toString(), description
                            .getText().toString(), color.getText().toString(), template.getText().toString(),
                            model.getIdByUri(uri), sharedPref.getInt(ID, 0));
                    Intent intentFirstAction = new Intent(AddDressActivity.this, CoreActivity.class);
                    startActivity(intentFirstAction);
                    break;

                case 1:
                    Clothual clothual = model.getClothualByID(id);
                    clothual.setType(spinnerValue);
                    clothual.setBrand(brand.getText().toString());
                    clothual.setDescription(description.getText().toString());
                    clothual.setColor(color.getText().toString());
                    clothual.setTemplate(template.getText().toString());
                    model.update(clothual);
                    Intent intentSecondAction = new Intent(AddDressActivity.this, CoreActivity.class);
                    startActivity(intentSecondAction);
                    break;

                default:
                    break;

            }
        });
    }

    @Override
    public void onBackPressed() {
        model = new AddDressModel(getApplication());
        String uri = getIntent().getExtras().getString("uri");
        contentResolver.delete(Uri.parse(uri),null ,null );
        List<Image> imageList = model.getAllImage();
        for(int i = 0; i < imageList.size(); i++){
            if(imageList.get(i).getUri().equals(uri)){
                model.deliteImage(imageList.get(i));
            }
        }
        super.onBackPressed();
    }
}
