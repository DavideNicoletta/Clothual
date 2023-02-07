package com.example.clothual.UI.welcome.LoginFragment;

import static com.example.clothual.Util.Constant.COCO_CHANEL;
import static com.example.clothual.Util.Constant.CREDENTIALS_LOGIN_FILE;
import static com.example.clothual.Util.Constant.DONATELLA_VERSACE;
import static com.example.clothual.Util.Constant.GIANNI_VERSACE;
import static com.example.clothual.Util.Constant.GIORGIO_ARMANI;
import static com.example.clothual.Util.Constant.ID;
import static com.example.clothual.Util.Constant.PASSWORD_PREFERENCE;
import static com.example.clothual.Util.Constant.PIER_CARDIN;
import static com.example.clothual.Util.Constant.RALPH_LAUREN;
import static com.example.clothual.Util.Constant.USERNAME_PREFERENCE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clothual.R;
import com.example.clothual.UI.core.CoreActivity;
import com.example.clothual.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


@SuppressWarnings("deprecation")
public class LoginFragment extends Fragment {


    private FragmentLoginBinding binding;
    Handler handler = new Handler();
    LoginModel loginModel;
    private Thread thread;
    boolean check = true;

    //
    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;

    ProgressDialog progressDialog;

    //Google
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient gsc;

    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

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
        SharedPreferences.Editor edit = sharedPref.edit();
        String username = sharedPref.getString(USERNAME_PREFERENCE, "");
        String password = sharedPref.getString(PASSWORD_PREFERENCE, "");
        binding.editTextUsername.setText(username);
        binding.editTextPassword.setText(password);


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

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextUsername.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                edit.putInt(ID, loginModel.getIDByEmail(email));
                edit.apply();
                progressDialog.show();
                if (email.isEmpty()){
                    progressDialog.cancel();
                    Toast.makeText(getContext(), R.string.login_mail_empty, Toast.LENGTH_SHORT).show();
                } if (password.isEmpty()){
                    progressDialog.cancel();
                    Toast.makeText(getContext(), R.string.login_password_empty, Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();

                                    Intent intet = new Intent(requireContext(), CoreActivity.class);
                                    startActivity(intet);
                                    getActivity().finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        binding.textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextUsername.getText().toString();
                //controllo
                if (email.isEmpty()){
                    Toast.makeText(getContext(), R.string.login_mail_empty, Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Sending Mail");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(getContext(), "Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        /*
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
        */

       binding.textViewRegister.setOnClickListener(view12 -> {

           //    thread.interrupt();

           Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_fragment_registration);
       });

       //Google
        checkGoogle();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

       binding.signInButton.setOnClickListener(view1 -> {
           Intent intet = gsc.getSignInIntent();
           startActivityForResult(intet, RC_SIGN_IN);
           /*
           Intent intent = new Intent(requireContext(), CoreActivity.class);
           startActivity(intent);
           getActivity().finish();
           */
       });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void checkGoogle(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            navigateToMainActivity();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(requireContext(), CoreActivity.class);
       // intent.putExtra("option", "old");
        startActivity(intent);
        getActivity().finish();
    }

    /*
    private void navigateToMainActivityNew(String nome, String email, String username) {
        Intent intent = new Intent(requireContext(), CoreActivity.class);
        intent.putExtra("option", "new");
        intent.putExtra("name", nome);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        startActivity(intent);
        getActivity().finish();
    }
    */

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
/*
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        String name = firebaseUser.getDisplayName();

 */
                        //check
                        if (authResult.getAdditionalUserInfo().isNewUser()){
                            //Account created
                            Snackbar.make(getView(), "Welcome to Clothual", Snackbar.LENGTH_SHORT).show();
                          //  navigateToMainActivityNew(name, email, uid);
                            navigateToMainActivity();
                        } else {
                            //Existing user - Logged In
                            Snackbar.make(getView(), "Welcome Back to Clothual", Snackbar.LENGTH_SHORT).show();
                            navigateToMainActivity();
                        }

                        //Start
                        //navigateToMainActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login failed
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
