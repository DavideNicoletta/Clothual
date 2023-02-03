package com.example.clothual.UI.core.Personal.Settings;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.DMODE;
import static com.example.clothual.Util.Constant.LANGUAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class SettingsFragment extends Fragment {

        public FragmentSettingsBinding binding;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        //suggerimento di android studio, non so cosa faccia


        public SettingsFragment() {
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static SettingsFragment newInstance() {
            return new SettingsFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = FragmentSettingsBinding.inflate(getLayoutInflater());
            binding.imageEnglish.setImageResource(R.drawable.circle_48px);
            binding.imageFrench.setImageResource(R.drawable.circle_48px);
            binding.imageItalian.setImageResource(R.drawable.circle_48px);

            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            Context context = getActivity();
            SharedPreferences share = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();

            if(!share.getString(LANGUAGE, " ").equals(" ")) {
                setImage(share.getString(LANGUAGE, " "));
            }else{
                setImage(Locale.getDefault().getLanguage());
            }
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

/*
                if (isNightModeActive(context)) {
                    binding.myswitch.setChecked(true);
                } else binding.myswitch.setChecked(false);*/

            Boolean booleanValue = share.getBoolean(DMODE, true);
            if(booleanValue){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                binding.myswitch.setChecked(true);
            }


                binding.myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        binding.myswitch.setChecked(true);
                        editor.putBoolean(DMODE, true);
                        editor.commit();
                        setMode();

                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        binding.myswitch.setChecked(false);
                        editor.putBoolean(DMODE, false);
                        editor.commit();
                        setMode();

                    }
                }
            });

        }


       @SuppressWarnings("deprecation")
        private void setLocale(String lang) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = getActivity().getResources().getConfiguration();
            config.locale = locale;
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
            Navigation.findNavController(requireView()).navigate(R.id.action_settingsFragment_self);

        }

   /* private void setMode(){
        recreate();
    }*/

        private void setMode() {
            Configuration config = getActivity().getResources().getConfiguration();
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
            recreate();
        }

        public static boolean isNightModeActive(Context context) {
            int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
            if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                return true;
            }
            if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                return false;
            }

            int currentNightMode = context.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    return false;
                case Configuration.UI_MODE_NIGHT_YES:
                    return true;
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                    return false;
            }
            return false;
        }


        public void setImage(String lang) {
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

        public void recreate(){

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.containerSettings, SettingsFragment.newInstance()).commit();


        }


}


