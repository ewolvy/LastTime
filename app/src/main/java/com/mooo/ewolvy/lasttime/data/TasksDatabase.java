package com.mooo.ewolvy.lasttime.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {TaskItem.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
