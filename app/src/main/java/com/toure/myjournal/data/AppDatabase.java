package com.toure.myjournal.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

/**
 * Created by Toure Nathan on 6/30/2018.
 */

@Database(entities = {Note.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final Object LOCK = new Object();
    private static final String LOG_TAC = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "myjournal_db";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAC, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        //Temporally allow main thread queries
                        //TODO: Remove main thread queries
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAC, "Getting the database instance");
        return sInstance;
    }

    public abstract NoteDao noteDao();

}
