package com.example.clothual.Database;

import static androidx.room.OnConflictStrategy.REPLACE;
import static com.example.clothual.Util.Query.GET_ALL_OUTFIT;
import static com.example.clothual.Util.Query.GET_OUTFIT_BY_DATE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.clothual.Model.Outfit;

import java.util.List;

@Dao
public interface OutfitDao {

    @Query(GET_ALL_OUTFIT)
    List<Outfit> getAlLOutfit();

    @Insert(onConflict = REPLACE)
    void insertOutfit(Outfit outfit);

    @Update
    void updateOutfit(Outfit outfit);

    @Delete
    void deliteOutfit(Outfit outfit);

    @Query(GET_OUTFIT_BY_DATE)
    Outfit getOutfitByDate(String date);

}
