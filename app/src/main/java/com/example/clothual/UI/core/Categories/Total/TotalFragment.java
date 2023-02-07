package com.example.clothual.UI.core.Categories.Total;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID;

import android.content.Context;
import android.content.Intent;
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
import com.example.clothual.UI.core.AddDress.AddDressActivity;
import com.example.clothual.UI.core.adapter.RecyclerViewClothualAdapter;
import com.example.clothual.UI.core.Categories.CategoryModel;
import com.example.clothual.databinding.FragmentTotalBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TotalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalFragment extends Fragment {

   public FragmentTotalBinding binding;
   public CategoryModel model;

    public TotalFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TotalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalFragment newInstance() {
        return new TotalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CategoryModel(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTotalBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(requireContext());
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        List<Image> image = model.getImageList(sharedPref.getInt(ID, 0));
        List<Clothual> clothual = model.getClothualList(sharedPref.getInt(ID, 0));
        RecyclerViewClothualAdapter adapter = new RecyclerViewClothualAdapter(clothual, image,
                getActivity().getContentResolver(), new RecyclerViewClothualAdapter.OnItemClickListener() {

            @Override
            public void buttonEdit(String uri, int action, int id) {
                Intent intent = new Intent(getActivity(), AddDressActivity.class);
                intent.putExtra("uri", uri);
                intent.putExtra("action", 1);
                intent.putExtra("id", id);
                startActivity(intent);
            }

            @Override
            public void buttonDelete(String notify) {
                Snackbar.make(view, notify, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void buttonFavorite(String favorite) {
                Snackbar.make(view, favorite, Snackbar.LENGTH_LONG).show();
            }
        }, getActivity().getApplication());
        binding.recyclerViewTotal.setLayoutManager(manager);
        binding.recyclerViewTotal.setAdapter(adapter);

    }

}