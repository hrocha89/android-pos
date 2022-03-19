package com.henrique.colecaodiscos.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.henrique.colecaodiscos.domain.Album;

import java.util.List;

@Dao
public interface AlbumDAO {

    @Insert
    long insert(Album album);

    @Update
    void update(Album album);

    @Delete
    void delete(Album album);

    @Query("SELECT * FROM album ORDER BY anoGravacao ASC")
    List<Album> findAll();

    @Query("SELECT * FROM album WHERE id = :id")
    Album findOne(long id);
}
