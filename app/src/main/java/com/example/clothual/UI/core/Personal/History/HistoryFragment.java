package com.example.clothual.UI.core.Personal.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothual.databinding.FragmentHistoryBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private HistoryModel historyModel;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyModel = new HistoryModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        float [] rate = historyModel.getRateTypeClothual();
        List<String> color = historyModel.getRateColor();
        //binding.mostUsedColor.setText("Most used Color: " + color[0] + ", " + color[1] + " %");


        switch (color.size()){
            case 2:
                binding.firstColorText.setText(color.get(0) + ": " + color.get(1) + "%");
                binding.firstColorRate.setProgress(Integer.parseInt(color.get(1)));
                break;
            case 4:
                binding.firstColorText.setText(color.get(0) + ": " + color.get(1) + "%");
                binding.firstColorRate.setProgress(Integer.parseInt(color.get(1)));
                binding.secondColorText.setText(color.get(2) + ": " + color.get(3) + "%");
                binding.secondColorRate.setProgress(Integer.parseInt(color.get(3)));
                break;
            case 6:
                binding.firstColorText.setText(color.get(0) + ": " + color.get(1) + "%");
                binding.firstColorRate.setProgress(Integer.parseInt(color.get(1)));
                binding.secondColorText.setText(color.get(2) + ": " + color.get(3) + "%");
                binding.secondColorRate.setProgress(Integer.parseInt(color.get(3)));
                binding.thirtColorText.setText(color.get(4) + ": " + color.get(5) + "%");
                binding.thirtColorRate.setProgress(Integer.parseInt(color.get(5)));
                break;
            default:
                break;
        }

        binding.shoesCircularProgressbar.setProgress((int)rate[0]);
        binding.rateShoes.setText((int)rate[0] + "%");

        binding.trousersCircularProgressbar.setProgress((int)rate[1]);
        binding.rateTrousers.setText((int)rate[1] + "%");

        binding.tShirtCircularProgressbar.setProgress((int)rate[2]);
        binding.rateTShirt.setText((int)rate[2] + "%");

        binding.jacketsCircularProgressbar.setProgress((int)rate[3]);
        binding.rateJackets.setText((int)rate[4] + "%");

        binding.jeansCircularProgressbar.setProgress((int)rate[4]);
        binding.rateJeans.setText((int)rate[4] + "%");
    }


}