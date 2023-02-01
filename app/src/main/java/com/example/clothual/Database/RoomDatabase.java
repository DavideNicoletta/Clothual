package com.example.clothual.Database;

import static com.example.clothual.Util.Constant.DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.clothual.Model.Account;
import com.example.clothual.Model.Clothual;
import com.example.clothual.Model.Image;
import com.example.clothual.Model.Outfit;
import com.example.clothual.Model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Account.class, Image.class, Clothual.class, Outfit.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract UserDao daoUser();
    public abstract AccountDao daoAccount();
    public abstract ImageDao imageDao();
    public abstract ClothualDao clothualDao();
    public abstract OutfitDao outfitDao();

    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
