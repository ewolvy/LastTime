package com.mooo.ewolvy.lasttime.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.mooo.ewolvy.lasttime.data.TaskDao;
import com.mooo.ewolvy.lasttime.data.TaskRepository;
import com.mooo.ewolvy.lasttime.data.TasksDatabase;
import com.mooo.ewolvy.lasttime.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Provides;

public class RoomModule {
    private final TasksDatabase database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                TasksDatabase.class,
                "tasks"
        ).build();
    }

    @Provides
    @Singleton
    TaskRepository provideListItemRepository(TaskDao taskDao){
        return new TaskRepository(taskDao);
    }

    @Provides
    @Singleton
    TaskDao provideListItemDao(TasksDatabase database){
        return database.taskDao();
    }

    @Provides
    @Singleton
    TasksDatabase provideListItemDatabase(Application application){
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(TaskRepository repository){
        return new CustomViewModelFactory(repository);
    }
}
