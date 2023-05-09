package com.example.clothual.Data.Source.User;

import com.example.clothual.Data.Repository.Welcome.UserResponseCallback;
import com.example.clothual.Model.UserModelFirebase;

public abstract class BaseUserAuthenticationRemoteDataSource {

    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract UserModelFirebase getLoggedUser();

    public abstract void logout();

    public abstract void signUp(String email, String password);

    public abstract void signIn(String email, String password);

    public abstract void signInWithGoogle(String idToken);

}
