package com.example.clothual.Data.Repository.Welcome;

import com.example.clothual.Model.User;
import com.example.clothual.Model.UserModelFirebase;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(UserModelFirebase user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onSuccessFromGettingUserPreferences();
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}

