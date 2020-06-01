package com.example.safewomen.RoomPack;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contacts.class},version = 1,exportSchema = false)
public abstract class Rdb extends RoomDatabase {
    public abstract MyDao myDao();
}

