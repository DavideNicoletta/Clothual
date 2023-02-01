package com.example.clothual.UI.welcome.LoginFragment;

import static com.example.clothual.Util.Constant.ACCESS_PREFERENCE;
import static com.example.clothual.Util.Constant.COCO_CHANEL;
import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.DONATELLA_VERSACE;
import static com.example.clothual.Util.Constant.GIANNI_VERSACE;
import static com.example.clothual.Util.Constant.GIORGIO_ARMANI;
import static com.example.clothual.Util.Constant.ID_ACCOUNT;
import static com.example.clothual.Util.Constant.PASSWORD_PREFERENCE;
import static com.example.clothual.Util.Constant.PIER_CARDIN;
import static com.example.clothual.Util.Constant.RALPH_LAUREN;
import static com.example.clothual.Util.Constant.USERNAME_PREFERENCE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.UI.core.CoreActivity;
import com.example.clothual.databinding.FragmentLoginBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class LoginFragment extends Fragment {


    private FragmentLoginBinding binding;
    Handler handler = new Handler();
    LoginModel loginModel;
    private Thread thread;
    boolean check = true;

    public LoginFragment() { }

    public static LoginFragment newInstance() {
      return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginModel = new LoginModel(requireActivity().getApplication());

       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        String username = sharedPref.getString(USERNAME_PREFERENCE, "");
        String password = sharedPref.getString(PASSWORD_PREFERENCE, "");
        binding.editTextUsername.setText(username);
        binding.editTextPassword.setText(password);
        //binding.signInButton.setColorScheme(SignInButton.COLOR_AUTO);


        Runnable runnable = new Runnable() {
            int i = 0;
            String [] strings = {GIANNI_VERSACE, RALPH_LAUREN, PIER_CARDIN, DONATELLA_VERSACE, GIORGIO_ARMANI, COCO_CHANEL};
            public void run() {
                do {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.changeText.setText(strings[i]);
                            if(i == 5){
                               i = 0;
                            }else{
                               i ++;
                            }
                        }
                    }, 0);
                    //Add some downtime
                    SystemClock.sleep(5000);
                }while (check);
            }
        };

        thread = new Thread(runnable);
        thread.start();

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(USERNAME_PREFERENCE, binding.editTextUsername.getText().toString());
                editor.putString(PASSWORD_PREFERENCE, binding.editTextPassword.getText().toString());
                editor.apply();

                if(loginModel.checkLogin(
                        binding.editTextUsername.getText().toString(),
                        binding.editTextPassword.getText().toString())
                ) {
                    editor.putInt(ACCESS_PREFERENCE, 1);
                    editor.putInt(ID_ACCOUNT, loginModel.idAccount(username));
                    editor.apply();
                    check = false;
                    Intent intent = new Intent(requireContext(), CoreActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    binding.inputViewUsername.setError("Errore");
                    binding.inputViewPassword.setError("Errore");
                }

            }
        });

       binding.textViewRegister.setOnClickListener(view12 -> {

           //    thread.interrupt();

           Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_fragment_registration);
       });

       binding.signInButton.setOnClickListener(view1 -> {
           Intent intent = new Intent(requireContext(), CoreActivity.class);
           startActivity(intent);
           getActivity().finish();
       });





    }
}
