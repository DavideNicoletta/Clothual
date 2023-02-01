package com.example.clothual.UI.core.Calendar;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.DATE;

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
import com.example.clothual.Model.Outfit;
import com.example.clothual.UI.core.adapter.RecyclerViewOutfitAddAdapter;
import com.example.clothual.databinding.FragmentAddClothualOutfitBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClothualOutfitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClothualOutfitFragment extends Fragment {

    String data;
    public FragmentAddClothualOutfitBinding binding;
    public CalendarModel model;

    public AddClothualOutfitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddClothualOutfitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClothualOutfitFragment newInstance() {
        return new AddClothualOutfitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CalendarModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddClothualOutfitBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        data = sharedPref.getString(DATE, "");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(manager);

        Outfit outfit = model.getOutfitByDate(data);


        if(outfit == null){
            List<Image> image = model.getImageList();
            List<Clothual> clothual = model.getClothualList();
            recycler(clothual, image, view);
        }else{
            List<Clothual> clothualList = model.getClothualOutfitDate(outfit);
            List<Image> imageOutfit = model.getImageOutfit(clothualList);
            recycler(clothualList, imageOutfit, view);
        }

    }

    public void recycler(List<Clothual> clothual, List<Image> image, View view){
        RecyclerViewOutfitAddAdapter adapter = new RecyclerViewOutfitAddAdapter(clothual, image,
                getActivity().getContentResolver(),
                add -> Snackbar.make(view, add, Snackbar.LENGTH_LONG).show(), getActivity().getApplication(), data);


        binding.recyclerView.setAdapter(adapter);
    }

}