package com.mooo.ewolvy.lasttime.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Insert(onConflict = REPLACE)
    void addTask(TaskItem item);

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    LiveData<TaskItem> getTask(int taskId);


}
