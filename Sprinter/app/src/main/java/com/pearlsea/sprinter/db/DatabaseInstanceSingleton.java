package com.pearlsea.sprinter.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

public class DatabaseInstanceSingleton {

    // Create an instance variable that stores the single instance of the class
    private static DatabaseInstanceSingleton instance;
    private static SprinterDatabase sprinterDatabase;

    @Nullable
    public static User activeUser = null;

    // Make the constructor private so it cannot be called from outside the class
    private DatabaseInstanceSingleton() {}

    // Create a method to get the single instance of the class
    public static DatabaseInstanceSingleton getInstance(Context context) {
        // Check if the instance has been created yet, and create it if it hasn't
        if (instance == null) {
            instance = new DatabaseInstanceSingleton();
            DatabaseSetup(context);
        }
        return instance;
    }

    private static void DatabaseSetup(Context context) {
        sprinterDatabase = Room.databaseBuilder(context, SprinterDatabase.class, "sprinter-database").build();
    }

    public static SprinterDatabase getDatabase() {
        if (sprinterDatabase == null) {
            Log.e("DatabaseInstanceSingleton", "The database has not yet been setup. Make sure to call getInstance() before trying to get the database.");
        }

        return sprinterDatabase;
    }
}