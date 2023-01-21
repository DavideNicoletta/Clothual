package com.example.clothual.UI.welcome.RegistrationFragment;

import static com.example.clothual.Util.Constant.ACCESS_PREFERENCE;
import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.ID_ACCOUNT;
import static com.example.clothual.Util.Constant.PASSWORD_PREFERENCE;
import static com.example.clothual.Util.Constant.POLICY;
import static com.example.clothual.Util.Constant.USERNAME_PREFERENCE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.UI.core.CoreActivity;
import com.example.clothual.databinding.FragmentRegistrationBinding;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    public RegistrationModel registrationModel;
    public RegistrationFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_registration.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationModel = new RegistrationModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(CREDENTIALS_LOGIN_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        binding.policy.setText(POLICY);

        binding.redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_registration_to_loginFragment);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkFields
                        (binding.editTextUsername.getText().toString(),
                        binding.editTextName.getText().toString(),
                        binding.editTextSurname.getText().toString(),
                        binding.editTextPassword.getText().toString(),
                        binding.editTextEmail.getText().toString()) == true
                )
                {
                    registrationModel.createUser(binding.editTextUsername.getText().toString(),
                            binding.editTextName.getText().toString(),
                            binding.editTextSurname.getText().toString(),
                            binding.editTextPassword.getText().toString(),
                            binding.editTextEmail.getText().toString());
                    editor.putString(USERNAME_PREFERENCE, binding.editTextUsername.getText().toString());
                    editor.putString(PASSWORD_PREFERENCE, binding.editTextPassword.getText().toString());
                    editor.putInt(ACCESS_PREFERENCE, 1);
                    editor.putInt(ID_ACCOUNT, registrationModel.idAccount(binding.editTextUsername.getText().toString()));
                    editor.apply();
                    Intent intet = new Intent(requireContext(), CoreActivity.class);
                    startActivity(intet);
                }
            }
        });

    }

    public boolean checkFields(String username, String name, String surname, String password, String email){
        int correct = 0;
        //ChechEmail
        if(EmailValidator.getInstance().isValid(email)){
            binding.inputViewEmail.setError(null);
            correct++;
        }else{
            binding.inputViewEmail.setError("Email non valida");
        }

        //Check Cognome
        if(surname.isEmpty()){
            binding.inputCognome.setError("Inserire un valore");
        }
        else{
            binding.inputCognome.setError(null);
            correct++;
        }

        //Check Nome
        if(name.isEmpty()){
            binding.inputNome.setError("Inserire un valore");
        } else{
            binding.inputCognome.setError(null);
            correct++;
        }

        //Check Username
        if(username.isEmpty()){
            binding.inputViewUsername.setError("Inserire un valore");
        } else{
            binding.inputCognome.setError(null);
            correct++;
        }

        //Check Password
        if(password.isEmpty()){
            binding.inputViewPassword.setError("Inserire un valore");
        } else{
            binding.inputCognome.setError(null);
            correct++;
        }

        if(correct == 5){
            return true;
        }else{
            return false;
        }
    }

}