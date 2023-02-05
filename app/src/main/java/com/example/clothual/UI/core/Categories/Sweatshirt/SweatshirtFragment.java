package com.example.clothual.UI.core.Categories.Sweatshirt;

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

import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.UI.core.AddDress.AddDressActivity;
import com.example.clothual.UI.core.Categories.CategoryModel;
import com.example.clothual.UI.core.adapter.RecyclerViewClothualAdapter;
import com.example.clothual.databinding.FragmentSweatshirtBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SweatshirtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SweatshirtFragment extends Fragment {

    private FragmentSweatshirtBinding binding;
    private CategoryModel model;

    public SweatshirtFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SweatshirtFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SweatshirtFragment newInstance() {
        return new SweatshirtFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CategoryModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSweatshirtBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(requireContext());

        List<Clothual> clothual = model.getJacketsList();
        List<Image> image = model.getImageJacketsList(clothual);
        RecyclerViewClothualAdapter adapter = new RecyclerViewClothualAdapter(clothual, image,
                getActivity().getContentResolver(), new RecyclerViewClothualAdapter.OnItemClickListener() {
            @Override
            public void buttonDelete(String notify) {
                Snackbar.make(view, notify, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void buttonFavorite(String favorite) {
                Snackbar.make(view, favorite, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void buttonEdit(String uri, int action, int id) {
                Intent intent = new Intent(getActivity(), AddDressActivity.class);
                intent.putExtra("uri", uri);
                intent.putExtra("action", 1);
                intent.putExtra("id", id);
                startActivity(intent);
            }

        }, getActivity().getApplication());
        binding.recyclerViewShoes.setLayoutManager(manager);
        binding.recyclerViewShoes.setAdapter(adapter);

    }

}