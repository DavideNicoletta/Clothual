package com.example.clothual.UI.core.Photo;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothual.Model.Image;
import com.example.clothual.R;
import com.example.clothual.UI.core.AddDress.AddDressActivity;
import com.example.clothual.UI.core.adapter.RecyclerViewPhotoAdapter;
import com.example.clothual.databinding.FragmentPhotoBinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("deprecation")
public class PhotoFragment extends Fragment {

    private FragmentPhotoBinding binding;

    public PhotoModel photoModel;
    private boolean isFABOpen;


    public PhotoFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoModel = new PhotoModel(requireActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotoBinding.inflate(getLayoutInflater());
        return binding.getRoot();


    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab1Upload.setVisibility(View.GONE);
        binding.fab2Make.setVisibility(View.GONE);
        binding.makePhoto.setVisibility(View.GONE);
        binding.upload.setVisibility(View.GONE);

        isFABOpen = false;

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    binding.fab1Upload.show();
                    binding.fab2Make.show();
                    binding.upload.setVisibility(View.VISIBLE);
                    binding.makePhoto.setVisibility(View.VISIBLE);

                    isFABOpen = true;
                }else{
                    binding.fab1Upload.hide();
                    binding.fab2Make.hide();
                    binding.upload.setVisibility(View.GONE);
                    binding.makePhoto.setVisibility(View.GONE);

                    isFABOpen = false;
                }
            }
        });

        RecyclerView.LayoutManager manager = new GridLayoutManager(requireContext(), 3);

            List<Image> image = photoModel.getImageList();
            RecyclerViewPhotoAdapter adapter = new RecyclerViewPhotoAdapter(image, new RecyclerViewPhotoAdapter.OnItemClickListener() {
                @Override
                public void delete() {

                }

            }, getActivity().getApplication(), getContext().getContentResolver());
            binding.recyclerView.setLayoutManager(manager);
            binding.recyclerView.setAdapter(adapter);


        binding.fab1Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        binding.fab2Make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("option", 0);
               // someActivityResultLauncher.launch(intent);
                startActivityForResult(intent, 0);
            }
        });

    }

    /*
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    assert intent != null;
                    int option = intent.getIntExtra("option", 0);
                    Uri uri;
                    if(option == 0) {
                        if (result == null) {

                            Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);

                        } else {
                            assert result != null;
                            Bitmap immagine = (Bitmap) intent.getExtras().get("data");

                            try {
                                uri = photoModel.saveImage(getActivity().getContentResolver(), immagine, photoModel.getNameImage(), "");
                                Intent intentToAdd = new Intent(getActivity(), AddDressActivity.class);
                                intent.putExtra("uri", uri.toString());
                                intent.putExtra("action", 0);
                                startActivity(intentToAdd);
                            } catch (IOException e) {

                                Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);

                            }

                        }
                    }else{
                        if (result == null) {
                            Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                        }else {
                            uri = intent.getData();
                            if (uri != null) {
                                try {
                                    Bitmap bitmap = photoModel.importImageFromMemory(getActivity(), getContext(), getActivity().getContentResolver(), uri);
                                    Uri newUri = photoModel.saveImage(getActivity().getContentResolver(), bitmap,
                                            photoModel.getNameImage(), "");
                                    Intent intenToAdd = new Intent(getActivity(), AddDressActivity.class);
                                    intent.putExtra("uri", newUri.toString());
                                    intent.putExtra("action", 0);
                                    startActivity(intenToAdd);
                                } catch (FileNotFoundException e) {
                                    Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                                } catch (IOException e) {
                                    Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                                }

                            }
                        }
                    }
                }
            }

    );*/

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent databack) {
        Uri uri;
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        if(requestCode == 0) {
            if (databack == null) {

                Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);

            } else {
                assert databack != null;
                Bitmap immagine = (Bitmap) databack.getExtras().get("data");

                try {
                    uri = photoModel.saveImage(getActivity().getContentResolver(), immagine, photoModel.getNameImage(), "", sharedPref.getInt(ID, 0));
                    Intent intent = new Intent(getActivity(), AddDressActivity.class);
                    intent.putExtra("uri", uri.toString());
                    intent.putExtra("action", 0);
                    startActivity(intent);
                } catch (IOException e) {

                    Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);

                }

             }
        }else{
            if (databack == null) {
                Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
            }else {
                    uri = databack.getData();
                    if (uri != null) {
                        try {
                            Bitmap bitmap = photoModel.importImageFromMemory(getActivity(), getContext(), getActivity().getContentResolver(), uri);
                            Uri newUri = photoModel.saveImage(getActivity().getContentResolver(), bitmap,
                                    photoModel.getNameImage(), "", sharedPref.getInt(ID, 0));
                            Intent intent = new Intent(getActivity(), AddDressActivity.class);
                            intent.putExtra("uri", newUri.toString());
                            intent.putExtra("action", 0);
                            startActivity(intent);
                        } catch (FileNotFoundException e) {
                            Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                        } catch (IOException e) {
                            Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                        }

                    }
                }
            }
    }
}

