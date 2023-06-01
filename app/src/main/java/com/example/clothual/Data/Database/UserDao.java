package com.example.clothual.Data.Database;

import static com.example.clothual.Util.Query.*;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.clothual.Model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query(SELECT_ALL_USER)
    List<User> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query(GET_USER_BY_UID)
    public User getUserByUid(String Uid);

    @Update
    public void updateUser(User user);

    @Query(GET_ID)
    public String getID(String username);

    @Query(GET_EMAIL)
    public String getEmail(String idUser);

    @Query(GET_USERNAME)
    public String getUsername(String idUser);

    @Query(GET_USER_BY_USERNAME)
    public User getUserByUsername(String username);

    @Query(GET_ID_BY_EMAIL)
    public String getIdByEmail(String email);

}
