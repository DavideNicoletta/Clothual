package com.example.clothual.Database;

import static com.example.clothual.Util.Query.GET_ACCOUNT_BY_ID;
import static com.example.clothual.Util.Query.GET_EMAIL;
import static com.example.clothual.Util.Query.GET_ID;
import static com.example.clothual.Util.Query.GET_ID_BY_EMAIL;
import static com.example.clothual.Util.Query.GET_PASSWORD;
import static com.example.clothual.Util.Query.GET_USERNAME;
import static com.example.clothual.Util.Query.SELECT_ALL_ACCOUNT;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.clothual.Model.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Query(SELECT_ALL_ACCOUNT)
    List<Account> getAllAccount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Query(GET_ID)
    int getId(String username);

    @Query(GET_EMAIL)
    String getEmail(int idAccount);

    @Query(GET_USERNAME)
    String getUsername(int idAccount);

    @Query(GET_PASSWORD)
    String getPassword(int idAccount);

    @Query(GET_ACCOUNT_BY_ID)
    Account getAccountID(int idAccount);

    @Update
    void updateAccount(Account account);

    @Query(GET_ID_BY_EMAIL)
    int getIdByEmail(String email);

}
