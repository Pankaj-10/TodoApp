package com.gesh.punkaj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gesh.punkaj.model.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Delete
    void delete(Todo todo);

    @Update
    void update(Todo todo);

    @Query("DELETE FROM Todo")
    void deleteAll();

    @Query("SELECT * FROM Todo ORDER BY priority DESC")
    LiveData<List<Todo>> getAllNotes();
}

