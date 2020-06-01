package com.example.womensaviours.RoomPack;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contacts.class},version = 1,exportSchema = false)
public abstract class Rdb extends RoomDatabase {
    public abstract MyDao myDao();
}

