package com.example.clothual.UI.welcome.RegistrationFragment;

import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.POLICY;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.databinding.FragmentRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    public RegistrationModel registrationModel;
    public RegistrationFragment() { }

    //
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

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


        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                binding.clothual.setImageResource(R.drawable.logo_white_on_backgroung);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                binding.clothual.setImageResource(R.drawable.logo_black_on_white);
                break;
        }

        binding.policy.setText(POLICY);

        //Login Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getContext());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString();
                String surname = binding.editTextSurname.getText().toString();
                String name = binding.editTextName.getText().toString();
                String username = binding.editTextUsername.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_registration_to_loginFragment);
                                progressDialog.cancel();

                                firebaseFirestore.collection("User")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(username, name, surname, email));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
            }
        });

        binding.redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_registration_to_loginFragment);
            }
        });

        /*binding.button.setOnClickListener(new View.OnClickListener() {
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
        }*/
    }

}
