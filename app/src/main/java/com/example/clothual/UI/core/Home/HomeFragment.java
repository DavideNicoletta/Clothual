package com.example.clothual.UI.core.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_filterFragment);

            }
        });*/


/*
        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFilter.setVisibility(View.INVISIBLE);
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFilter.setVisibility(View.INVISIBLE);
            }
        });


        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    binding.viewFilter.setVisibility(View.VISIBLE);

            }
        });
*/

        binding.map.setOnClickListener(view12 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_mapFragment));

        binding.shoes.setOnClickListener(view1 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_shoesFragment));

        binding.total.setOnClickListener(view13 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_totalFragment));

        binding.favorite.setOnClickListener(view14 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_preferiteFragment));

        binding.trousers.setOnClickListener(view15 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_trousersFragment));

        binding.tShirt.setOnClickListener(view16 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_TShirtFragment));

        binding.jackets.setOnClickListener(view17 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_jacketsFragment));

        binding.jeans.setOnClickListener(view18 -> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_jeansFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}