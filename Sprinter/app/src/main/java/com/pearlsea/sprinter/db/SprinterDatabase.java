package com.pearlsea.sprinter.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class SprinterDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

