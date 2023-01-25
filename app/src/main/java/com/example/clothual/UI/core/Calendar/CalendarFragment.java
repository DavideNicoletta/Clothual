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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.R;
import com.example.clothual.UI.core.adapter.RecyclerViewCalendarAdapter;
import com.example.clothual.databinding.FragmentCalendarBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    String date;

    public CalendarModel model;
    private FragmentCalendarBinding binding;

    public CalendarFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance() {
       return new CalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CalendarModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DateFormat obj = new SimpleDateFormat("d/M/yyyy");
        Date res = new Date(binding.calendarView.getDate());
        date = "" + obj.format(res);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(manager);
        model.checkOutfitList();
        recycler(view);

        binding.addButton.setOnClickListener(view1 -> {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(DATE, date);
            editor.apply();
            Navigation.findNavController(requireView()).navigate(R.id.action_calendarFragment_to_addClothualOutfitFragment);

        });

        binding.calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            date = i2 + "/" + (i1+1) + "/" + i;
            recycler(view);
        });
    }

    public void recycler(View view){
        model.checkOutfitList();
        List<Clothual> clothual = model.getClothualOutfit(date);

        if(clothual != null) {

            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.empty.setVisibility(View.GONE);
            List<Image> image = model.getImageOutfit(clothual);
            RecyclerViewCalendarAdapter adapter = new RecyclerViewCalendarAdapter(clothual, image,
                    getActivity().getContentResolver(), (delite, cancel) -> {
                        if (delite) {
                            Snackbar.make(view, cancel, Snackbar.LENGTH_LONG).show();
                            recycler(view);
                        }else{
                            Snackbar.make(view, cancel, Snackbar.LENGTH_LONG).show();
                        }
                    }, getActivity().getApplication(), date);
            binding.recyclerView.setAdapter(adapter);
        }else{
            binding.recyclerView.setVisibility(View.GONE);
            binding.empty.setVisibility(View.VISIBLE);
        }
    }

}