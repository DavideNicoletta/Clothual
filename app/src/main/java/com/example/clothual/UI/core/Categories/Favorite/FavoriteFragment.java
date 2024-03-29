package com.example.clothual.UI.core.Categories.Favorite;

import static com.example.clothual.Util.Constant.ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothual.Adapter.RecyclerViewFavoriteAdapter;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.UI.core.Categories.CategoryModel;
import com.example.clothual.UI.core.Categories.ClothualElementShow;
import com.example.clothual.Util.SharedPreferenceReadWrite;
import com.example.clothual.databinding.FragmentFavoriteBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    public CategoryModel model;
    private SharedPreferenceReadWrite sharedPreferenceReadWrite;

    public FavoriteFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance() {
       return new FavoriteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CategoryModel(getActivity().getApplication());
        sharedPreferenceReadWrite = new SharedPreferenceReadWrite(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(requireContext());
        List<Clothual> clothual = model.getPreferiteList(sharedPreferenceReadWrite.readString(ID));
        List<Image> image = model.getImagePreferiteList(clothual, sharedPreferenceReadWrite.readString(ID));
        RecyclerViewFavoriteAdapter adapter = new RecyclerViewFavoriteAdapter(clothual, image,
                getActivity().getContentResolver(), new RecyclerViewFavoriteAdapter.OnItemClickListener() {
            @Override
            public void buttonFavorite(String favorite) {
                Snackbar.make(view, favorite, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void change(Clothual clothual, Image image) {
                Intent intent = new Intent(getActivity(), ClothualElementShow.class);
                intent.putExtra("type", clothual.getType());
                intent.putExtra("brand", clothual.getBrand());
                intent.putExtra("template", clothual.getTemplate());
                intent.putExtra("color", clothual.getColor());
                intent.putExtra("description", clothual.getDescription());
                intent.putExtra("uri", image.getUri());
                startActivity(intent);
            }
        }, getActivity().getApplication());

        binding.recyclerViewPreferite.setLayoutManager(manager);
        binding.recyclerViewPreferite.setAdapter(adapter);
    }
}