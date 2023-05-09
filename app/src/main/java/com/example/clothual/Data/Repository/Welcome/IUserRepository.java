package com.example.clothual.Data.Repository.Welcome;

public interface IUserRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void signInWithGoogle(String token);

}
