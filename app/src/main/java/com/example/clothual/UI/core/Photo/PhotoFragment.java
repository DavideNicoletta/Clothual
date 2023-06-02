package com.example.clothual.UI.core.Photo;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.clothual.Adapter.RecyclerViewPhotoAdapter;
import com.example.clothual.databinding.FragmentPhotoBinding;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        binding.fab.setOnClickListener(view1 -> {
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
        });

        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        RecyclerView.LayoutManager manager;
        switch (rotation){
            case 0:
                manager = new GridLayoutManager(requireContext(), 2);
                break;
            case 1:
                manager = new GridLayoutManager(requireContext(), 4);
                break;
            default:
                manager = new GridLayoutManager(requireContext(), 2);
        }

            SharedPreferences sharedPref = getActivity().getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
            String Id = sharedPref.getString(ID, "");
            List<Image> image = photoModel.getImageList(Id);
            RecyclerViewPhotoAdapter adapter = new RecyclerViewPhotoAdapter(image, new RecyclerViewPhotoAdapter.OnItemClickListener() {
                @Override
                public void delete() {

                }

                @Override
                public void change(Image image) {
                    Intent intent = new Intent(getActivity(), ShowPhoto.class);
                    intent.putExtra("uri", image.getUri());
                    startActivity(intent);
                }
            }, getActivity().getApplication(), getContext().getContentResolver());
            binding.recyclerView.setLayoutManager(manager);
            binding.recyclerView.setAdapter(adapter);


        binding.fab1Upload.setOnClickListener(view13 -> imageChooser());

        binding.fab2Make.setOnClickListener(view12 -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityTakeResultLauncher.launch(intent);
        });

    }


    ActivityResultLauncher<Intent> activityTakeResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri uri;
                        Context context = getActivity();
                        assert context != null;
                        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
                        Intent data = result.getData();
                        if (data == null) {
                            Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                        }else{
                            Bitmap immagine = (Bitmap) data.getExtras().get("data");
                            try {
                                uri = photoModel.saveImage(getActivity().getContentResolver(), immagine, photoModel.getNameImage(), "", sharedPref.getString(ID, ""));
                                Intent intent = new Intent(getActivity(), AddDressActivity.class);
                                intent.putExtra("uri", uri.toString());
                                intent.putExtra("action", 0);
                                startActivity(intent);
                            } catch (IOException e) {
                                Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
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
                        Context context = getActivity();
                        assert context != null;
                        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
                        if (data == null) {
                            Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
                        }else{
                            uri = data.getData();
                            if (uri != null) {
                                try {
                                    Bitmap bitmap = photoModel.importImageFromMemory(getActivity(), getContext(), getActivity().getContentResolver(), uri);
                                    Uri newUri = photoModel.saveImage(getActivity().getContentResolver(), bitmap,
                                            photoModel.getNameImage(), "", sharedPref.getString(ID, ""));
                                    Intent intent = new Intent(getActivity(), AddDressActivity.class);
                                    intent.putExtra("uri", newUri.toString());
                                    intent.putExtra("action", 0);
                                    startActivity(intent);
                                } catch (IOException e) {
                                    Navigation.findNavController(requireView()).navigate(R.id.action_photoFragment_to_homeFragment);
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

