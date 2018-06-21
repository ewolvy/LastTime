package com.mooo.ewolvy.lasttime.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Insert(onConflict = REPLACE)
    void addTask(TaskItem item);

    @Query("SELECT * FROM tasks ORDER BY position")
    LiveData<List<TaskItem>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskItem getTaskById(int id);

    @Update
    void updateTasks(TaskItem... items);
}
