package com.mooo.ewolvy.lasttime.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Insert(onConflict = REPLACE)
    void addTask(TaskItem... item);

    @Query("SELECT * FROM tasks ORDER BY position")
    LiveData<List<TaskItem>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskItem> getTaskById(int id);

    @Query("SELECT * FROM tasks WHERE remindOn < :date")
    LiveData<List<TaskItem>> getRemindedTasks(Date date);

    @Update
    void updateTasks(TaskItem... items);

    @Delete
    void deleteTask(TaskItem... items);
}
