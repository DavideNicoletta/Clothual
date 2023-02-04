package com.example.clothual.UI.core.Personal.EditProfile;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID_ACCOUNT;
import static com.example.clothual.Util.Constant.URI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.Model.Account;
import com.example.clothual.R;
import com.example.clothual.UI.core.AddDress.AddDressActivity;
import com.example.clothual.UI.core.CoreActivity;
import com.example.clothual.databinding.EditProfileLayoutBinding;

import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class EditProfileActivity extends AppCompatActivity {

    private EditProfileLayoutBinding binding;
    private EditProfileModel modifyModel;
    public SharedPreferences share;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditProfileLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        modifyModel = new EditProfileModel(getApplication());
        share = getSharedPreferences(CREDENTIALS_LOGIN_FILE, MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);

        if(sharedPref.getString(URI, " ").equals(" ")){
            binding.imagePersonal.setImageResource(R.drawable.avatar);
        }else{
            try {
                binding.imagePersonal.setImageBitmap(modifyModel.importImageFromMemory(this, getApplicationContext(), getContentResolver(),
                        Uri.parse(sharedPref.getString(URI, " "))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        binding.editTextEmail.setText(modifyModel.getEmail(sharedPref.getInt(ID_ACCOUNT, 0)));
        binding.editTextUsername.setText(modifyModel.getUsername(sharedPref.getInt(ID_ACCOUNT, 0)));

        binding.editTextEmail.setEnabled(false);
        binding.editTextPassword.setEnabled(false);
        binding.editTextUsername.setEnabled(false);
        binding.editTextPasswordNuovo.setEnabled(false);

        binding.total.setOnClickListener(view19 -> binding.viewUpload.setVisibility(View.INVISIBLE));

        binding.modifyImage.setOnClickListener(view13 -> binding.viewUpload.setVisibility(View.VISIBLE));

        binding.upload.setOnClickListener(view12 -> {
            binding.viewUpload.setVisibility(View.INVISIBLE);
            imageChooser();
        });

        binding.makePhoto.setOnClickListener(view14 -> {
            binding.viewUpload.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        });

        binding.modificaEmail.setOnClickListener(view15 -> {
            if(binding.editTextEmail.isEnabled()){
                binding.editTextEmail.setEnabled(false);
                binding.editTextEmail.setText("");
                binding.modificaEmail.setText(R.string.edit);
            }else{
                binding.modificaEmail.setText(R.string.cancel);
                binding.editTextEmail.setEnabled(true);
            }
        });

        binding.modificaUsername.setOnClickListener(view16 -> {
            if(binding.editTextUsername.isEnabled()){
                binding.editTextUsername.setEnabled(false);
                binding.editTextUsername.setText("");
                binding.modificaUsername.setText(R.string.edit);
            }else{
                binding.modificaUsername.setText(R.string.cancel);
                binding.editTextUsername.setEnabled(true);
            }

        });

        binding.modificaPassword.setOnClickListener(view17 -> {
            if(binding.editTextPassword.isEnabled()){
                binding.editTextPassword.setEnabled(false);
                binding.editTextPasswordNuovo.setEnabled(false);
                binding.editTextPassword.setText("");
                binding.editTextPasswordNuovo.setText("");
                binding.modificaPassword.setText(R.string.edit);
            }else{
                binding.modificaPassword.setText(R.string.cancel);
                binding.editTextPassword.setEnabled(true);
                binding.editTextPasswordNuovo.setEnabled(true);
            }

        });


        binding.close.setOnClickListener(view18 -> {
            int id = sharedPref.getInt(ID_ACCOUNT, 0);
            Account account = modifyModel.getAccountByID(id);
            if(binding.editTextEmail.isEnabled()){
                if(modifyModel.checkEmail(binding.editTextEmail.getText().toString())){
                    binding.inputViewEmail.setError(null);
                    //editor.putString(EMAIL_CHANGE, binding.editTextEmail.getText().toString());
                    account.setEmail(binding.editTextEmail.getText().toString());
                }else{
                    binding.inputViewEmail.setError("Mail non valida");
                    //editor.putString(EMAIL_CHANGE, modifyModel.getEmail(sharedPref.getInt(ID_ACCOUNT, 0)));
                }
            }

            if(binding.editTextUsername.isEnabled()){
                if(binding.editTextUsername.getText().toString().isEmpty()){
                    binding.inputUsername.setError("Username non valido");

                }else{
                    binding.inputUsername.setError(null);
                    account.setUsername(binding.editTextUsername.getText().toString());
                    //editor.putString(USERNAME_CHANGE, binding.editTextUsername.getText().toString());
                }
            }

            if(binding.editTextPassword.isEnabled()){
                if(modifyModel.checkPassword(binding.editTextUsername.getText().toString(), sharedPref.getInt(ID_ACCOUNT, 0))){
                    if(binding.editTextPasswordNuovo.getText().toString().isEmpty()){
                        binding.inputPassword.setError("Password non valida");

                    }else{
                        binding.inputPassword.setError(null);
                        //editor.putString(PASSWORD_CHANGE, );
                        account.setPassword(binding.editTextPasswordNuovo.getText().toString());
                    }
                }else{
                    binding.inputPassword.setError("Password Errata");

                }
            }

            modifyModel.upoloadEditAccount(account);
        });

        binding.cancel.setOnClickListener(view1 -> {
            Intent intent = new Intent(EditProfileActivity.this, CoreActivity.class);
            startActivity(intent);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent databack) {
        super.onActivityResult(requestCode, resultCode, databack);

        SharedPreferences.Editor editor = share.edit();

        Uri uri;
        if (requestCode == 0) {
            if (databack == null) {
               Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
               startActivity(intent);
            }else {
                assert databack != null;
                Bitmap immagine = (Bitmap) databack.getExtras().get("data");

                try {
                    editor.putString(URI, modifyModel.saveImage(getContentResolver(), immagine, modifyModel.getNameImage(), "profile").toString());
                    editor.apply();
                    binding.imagePersonal.setImageBitmap(immagine);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }else{
            if (databack == null) {
                Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
            uri = databack.getData();
            if(uri != null){
                modifyModel.createImage(modifyModel.getNameImage(), "", uri.toString());
                try {
                    Bitmap bitmap = modifyModel.importImageFromMemory(this, getApplicationContext(), getContentResolver(), uri);
                    Uri newUri = modifyModel.saveImage(getContentResolver(), bitmap,
                            modifyModel.getNameImage(), "profile");
                    Intent intent = new Intent(this, AddDressActivity.class);
                    intent.putExtra("uri", newUri.toString());
                    startActivity(intent);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
    }


}
