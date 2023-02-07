package com.example.clothual.Database;

import static com.example.clothual.Util.Query.*;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.clothual.Model.Clothual;

import java.util.List;

@Dao
public interface ClothualDao {

    @Insert
    void insertClothual(Clothual clothual);

    @Query(SELECT_ALL_CLOTHAUL)
    List<Clothual> getAllClothual();


    @Query(GET_CLOTUAL_BY_ID)
    Clothual getClothualByID(int idClothual);

    @Delete
    void deleteClothual(Clothual clothual);

    @Update
    void updateClothual(Clothual clothual);

}
