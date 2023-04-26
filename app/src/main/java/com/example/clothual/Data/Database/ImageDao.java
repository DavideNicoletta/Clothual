package com.example.clothual.Data.Database;

import static com.example.clothual.Util.Query.GET_ID_BY_URI;
import static com.example.clothual.Util.Query.SELECT_ALL_IMAGE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.clothual.Model.Image;

import java.util.List;

@Dao
public interface ImageDao {


    @Insert
    void insertImage(Image image);

    @Query(SELECT_ALL_IMAGE)
    List<Image> getAllImage();

    @Query(GET_ID_BY_URI)
    int getIDByUri(String uri);

    @Delete
    void deleteImage(Image image);

}
