package com.example.safewomen.RoomPack;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Query("Select * from contacts_table")
    List<Contacts> getAllData();


    @Insert
    void insert(Contacts rd);

    @Query("DELETE FROM contacts_table")
    void deleteAll();


    @Query("SELECT * from contacts_table ORDER BY name ASC")
    List<Contacts> getAlphabetizedWords();


}
