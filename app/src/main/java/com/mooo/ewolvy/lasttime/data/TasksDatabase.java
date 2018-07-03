package com.mooo.ewolvy.lasttime.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {TaskItem.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TasksDatabase extends RoomDatabase {
    /*private static final String DATABASE_NAME = "tasks";

    private static final Object LOCK = new Object();
    private static volatile TasksDatabase sInstance;

    public static TasksDatabase getInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TasksDatabase.class, TasksDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }*/

    public abstract TaskDao taskDao();
}
