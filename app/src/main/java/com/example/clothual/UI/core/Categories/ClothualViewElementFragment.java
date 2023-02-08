package com.example.clothual.UI.core.Categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothual.databinding.FragmentClothualViewElementBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClothualViewElementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClothualViewElementFragment extends Fragment {


    private FragmentClothualViewElementBinding binding;

    public ClothualViewElementFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClothualViewElementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClothualViewElementFragment newInstance() {
        return new ClothualViewElementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentClothualViewElementBinding.inflate(getLayoutInflater());
      return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}