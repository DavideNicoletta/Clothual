package com.example.clothual.UI.core.Personal.EditProfile;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID;
import static com.example.clothual.Util.Constant.ID_ACCOUNT;
import static com.example.clothual.Util.Constant.URI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.Model.User;
import com.example.clothual.R;
import com.example.clothual.UI.core.CoreActivity;
import com.example.clothual.UI.core.Personal.PersonalModel;
import com.example.clothual.Util.SharedPreferenceReadWrite;
import com.example.clothual.databinding.EditProfileLayoutBinding;

import java.io.FileNotFoundException;
import java.io.IOException;


public class EditProfileActivity extends AppCompatActivity {

    private EditProfileLayoutBinding binding;
    private PersonalModel personalModel;
    private SharedPreferenceReadWrite sharedPreferenceReadWrite;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditProfileLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        personalModel = new PersonalModel(getApplication());
        sharedPreferenceReadWrite = new SharedPreferenceReadWrite(getApplication());
        SharedPreferences sharedPref = getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);

        if(sharedPref.getString(URI, " ").equals(" ")){
            binding.imagePersonal.setImageResource(R.drawable.avatar);
        }else{
            try {
                binding.imagePersonal.setImageBitmap(personalModel.importImageFromMemory(this, getApplicationContext(), getContentResolver(),
                        Uri.parse(sharedPref.getString(URI, " "))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        binding.editTextEmail.setText(personalModel.getEmail(sharedPref.getString(ID_ACCOUNT, "")));
        binding.editTextUsername.setText(personalModel.getUsername(sharedPref.getString(ID_ACCOUNT, "")));

        binding.editTextEmail.setEnabled(false);
        binding.editTextPassword.setEnabled(false);
        binding.editTextUsername.setEnabled(false);
        binding.editTextPasswordNuovo.setEnabled(false);

        binding.total.setOnClickListener(view19 -> binding.viewUpload.setVisibility(View.INVISIBLE));

        binding.modifyImage.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view13) {
                                                       binding.viewUpload.setVisibility(View.VISIBLE);
                                                       binding.modifyImage.setVisibility(View.INVISIBLE);
                                                   }
                                               });


        binding.upload.setOnClickListener(view12 -> {
            binding.viewUpload.setVisibility(View.INVISIBLE);
            binding.modifyImage.setVisibility(View.VISIBLE);
            imageChooser();
        });

        binding.makePhoto.setOnClickListener(view14 -> {
            binding.viewUpload.setVisibility(View.INVISIBLE);
            binding.modifyImage.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityTakeResultLauncher.launch(intent);
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


        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view18) {
                String id = sharedPref.getString(ID_ACCOUNT, "");
                User user = personalModel.getUserByID(id);
                if (binding.editTextEmail.isEnabled()) {
                    if (personalModel.checkEmail(binding.editTextEmail.getText().toString())) {
                        binding.inputViewEmail.setError(null);
                        user.setEmail(binding.editTextEmail.getText().toString());
                    } else {
                        binding.inputViewEmail.setError("Mail non valida");
                    }
                }

                if (binding.editTextUsername.isEnabled()) {
                    if (binding.editTextUsername.getText().toString().isEmpty()) {
                        binding.inputUsername.setError("Username non valido");

                    } else {
                        binding.inputUsername.setError(null);
                        user.setUsername(binding.editTextUsername.getText().toString());
                    }
                }

                if (binding.editTextPassword.isEnabled()) {
                    if (personalModel.checkPassword(binding.editTextUsername.getText().toString(), sharedPref.getString(ID_ACCOUNT, ""))) {
                        if (binding.editTextPasswordNuovo.getText().toString().isEmpty()) {
                            binding.inputPassword.setError("Password non valida");

                        } else {
                            binding.inputPassword.setError(null);
                            user.setPassword(binding.editTextPasswordNuovo.getText().toString());
                        }
                    } else {
                        binding.inputPassword.setError("Password Errata");

                    }
                }

                personalModel.updateUser(user);
            }
        });

        binding.cancel.setOnClickListener(view1 -> {
            Intent intent = new Intent(EditProfileActivity.this, CoreActivity.class);
            startActivity(intent);
        });

    }


    ActivityResultLauncher<Intent> activityTakeResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Uri uri;
                        //Context context = getActivity();
                        //assert context != null;
                        //SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
                        Intent data = result.getData();
                        if (data == null) {
                            Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
                            startActivity(intent);
                        }else {
                           // assert databack != null;
                            Bitmap immagine = (Bitmap) data.getExtras().get("data");

                            try {
                                sharedPreferenceReadWrite.writeString(URI, personalModel.saveImage(getContentResolver(), immagine,
                                        personalModel.getNameImage(), "profile", sharedPreferenceReadWrite.readString(ID)).toString());
                                binding.imagePersonal.setImageBitmap(immagine);
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> activityUploadResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri;
                        if (data == null) {
                            Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
                            startActivity(intent);
                        }else{
                            uri = data.getData();
                            if (uri != null) {
                                try {
                                    Bitmap bitmap = personalModel.importImageFromMemoryEditProfile(EditProfileActivity.this, getApplicationContext(), getContentResolver(), uri);
                                    Uri newUri = personalModel.saveImage(getContentResolver(), bitmap,
                                            personalModel.getNameImage(), "profile", sharedPreferenceReadWrite.readString(ID));
                                    binding.imagePersonal.setImageBitmap(bitmap);
                                    sharedPreferenceReadWrite.writeString(URI, newUri.toString());
                                } catch (IOException e) {
                                    Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
            });


    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activityUploadResultLauncher.launch(Intent.createChooser(i, "Select Picture"));
    }


}
