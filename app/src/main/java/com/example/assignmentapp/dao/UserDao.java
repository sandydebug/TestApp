package com.example.assignmentapp.dao;




import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.assignmentapp.POJO;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM country")
    List<POJO> getAll();


    @Insert
    void insertAll(List<POJO> list);

    @Delete
    void DeleteAll(List<POJO> list);

}
