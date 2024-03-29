package com.example.clothual.UI.welcome.LogoFragment;


import static com.example.clothual.Util.Constant.ACCESS_PREFERENCE;
import static com.example.clothual.Util.Constant.LANGUAGE;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.UI.core.CoreActivity;
import com.example.clothual.Util.SharedPreferenceReadWrite;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogoFragment extends Fragment {
    Handler handler = new Handler();
    public SharedPreferenceReadWrite sharedPreferenceReadWrite;
    public LogoFragment() {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment logo_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogoFragment newInstance() {
        LogoFragment fragment = new LogoFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_logo, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();
        sharedPreferenceReadWrite = new SharedPreferenceReadWrite(requireActivity().getApplication());
        String lenguage = sharedPreferenceReadWrite.readString(LANGUAGE);
        if(!lenguage.equals(" ")){
            setLocale(lenguage);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkGoogle();
            }
        }, 500);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int check = sharedPreferenceReadWrite.readInt(ACCESS_PREFERENCE);
                if(check == 1){
                    Intent intent = new Intent(requireContext(), CoreActivity.class);
                    startActivity(intent);
                }else{
                    Navigation.findNavController(requireView()).navigate(R.id.action_logoFragment_to_loginFragment);
                }

            }
        }, 500);
    }


    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getActivity().getResources().getConfiguration();
        config.setLocale(locale);
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());

    }

    void checkGoogle(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            navigateToMainActivity();
        }
    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(requireContext(), CoreActivity.class);

        startActivity(intent);
        getActivity().finish();
    }

}