package com.example.clothual.UI.core.Personal.Settings;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.LANGUAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.databinding.FragmentSettingsBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends AppCompatActivity {
    private class SettingsFragment extends Fragment {

        public FragmentSettingsBinding binding;
        @SuppressLint("UseSwitchCompatOrMaterialCode") //suggerimento di android studio, non so cosa faccia
        private Switch myswitch;


    public SettingsFragment() {
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static SettingsFragment newInstance () {
            return new SettingsFragment();
        }

        @Override
        public void onCreate (Bundle savedInstanceState){
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                setTheme(R.style.darktheme);
            } else setTheme(R.style.lighttheme);
            super.onCreate(savedInstanceState);
            myswitch = (Switch) myswitch.findViewById(R.id.myswitch);
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                myswitch.setChecked(true);
            }
            myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        restartApp();
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        restartApp();
                    }
                }
            });

        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            binding = FragmentSettingsBinding.inflate(getLayoutInflater());
            binding.imageEnglish.setImageResource(R.drawable.circle_48px);
            binding.imageFrench.setImageResource(R.drawable.circle_48px);
            binding.imageItalian.setImageResource(R.drawable.circle_48px);
            return binding.getRoot();
        }

        @Override
        public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);

            Context context = getActivity();
            SharedPreferences share = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();

            setImage(share.getString(LANGUAGE, " "));


            binding.english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString(LANGUAGE, "en");
                    editor.apply();
                    setLocale("en");
                }
            });

            binding.italian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString(LANGUAGE, "it");
                    editor.apply();
                    setLocale("it");
                }
            });

            binding.french.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString(LANGUAGE, "fr");
                    editor.apply();
                    setLocale("fr");
                }
            });

            binding.darkmode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.darkmode.setChecked(true);
                    binding.light.setChecked(false);
                    binding.system.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            });

            binding.light.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.darkmode.setChecked(false);
                    binding.light.setChecked(true);
                    binding.system.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });

            binding.system.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.darkmode.setChecked(false);
                    binding.light.setChecked(false);
                    binding.system.setChecked(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }
            });

        }

        @SuppressWarnings("deprecation")
        public void restartApp () {
            Intent i = new Intent(getContext(), Settings.class);
            startActivity(i);
            finish();
        }

        private void setLocale (String lang){
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = getActivity().getResources().getConfiguration();
            config.locale = locale;
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
            Navigation.findNavController(requireView()).navigate(R.id.action_settingsFragment_self);

        }

        public void setImage (String lang){
            switch (lang) {
                case "en":
                    binding.imageEnglish.setImageResource(R.drawable.total_circle_48px__1_);
                    binding.imageItalian.setImageResource(R.drawable.circle_48px);
                    binding.imageFrench.setImageResource(R.drawable.circle_48px);
                    break;
                case "it":
                    binding.imageItalian.setImageResource(R.drawable.total_circle_48px__1_);
                    binding.imageEnglish.setImageResource(R.drawable.circle_48px);
                    binding.imageFrench.setImageResource(R.drawable.circle_48px);
                    break;
                case "fr":
                    binding.imageFrench.setImageResource(R.drawable.total_circle_48px__1_);
                    binding.imageItalian.setImageResource(R.drawable.circle_48px);
                    binding.imageEnglish.setImageResource(R.drawable.circle_48px);
                    break;
                default:
                    binding.imageFrench.setImageResource(R.drawable.circle_48px);
                    binding.imageItalian.setImageResource(R.drawable.circle_48px);
                    binding.imageEnglish.setImageResource(R.drawable.circle_48px);
            }
        }
    }
}