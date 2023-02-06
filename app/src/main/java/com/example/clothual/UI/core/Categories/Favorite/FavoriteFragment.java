package com.example.clothual.UI.core.Categories.Favorite;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.UI.core.Categories.CategoryModel;
import com.example.clothual.UI.core.adapter.RecyclerViewFavoriteAdapter;
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

    public FavoriteFragment() {
        // Required empty public constructor
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
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        List<Clothual> clothual = model.getPreferiteList(sharedPref.getInt(ID, 0));
        List<Image> image = model.getImagePreferiteList(clothual, sharedPref.getInt(ID, 0));
        RecyclerViewFavoriteAdapter adapter = new RecyclerViewFavoriteAdapter(clothual, image,
                getActivity().getContentResolver(), new RecyclerViewFavoriteAdapter.OnItemClickListener() {
            @Override
            public void buttonFavorite(String favorite) {
                Snackbar.make(view, favorite, Snackbar.LENGTH_LONG).show();
            }
        }, getActivity().getApplication());

        binding.recyclerViewPreferite.setLayoutManager(manager);
        binding.recyclerViewPreferite.setAdapter(adapter);
    }
}