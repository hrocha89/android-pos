package com.henrique.colecaodiscos.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.henrique.colecaodiscos.domain.Album;

@Database(entities = {Album.class}, version = 1, exportSchema = false)
public abstract class AlbumDB extends RoomDatabase {

    public abstract AlbumDAO albumDAO();

    private static AlbumDB instance;

    public static AlbumDB getDataBase(final Context context) {

        if (instance == null) {
            synchronized (AlbumDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AlbumDB.class, "album.db")
                            .allowMainThreadQueries().build();
                }
            }
        }

        return instance;
    }

}
