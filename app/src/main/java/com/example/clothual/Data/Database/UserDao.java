package com.example.clothual.Data.Database;

import static com.example.clothual.Util.Query.SELECT_ALL_USER;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.clothual.Model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query(SELECT_ALL_USER)
    List<User> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

}
