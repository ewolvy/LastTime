package com.mooo.ewolvy.lasttime.di;

import android.app.Application;

import com.mooo.ewolvy.lasttime.view.TaskDetailFragment;
import com.mooo.ewolvy.lasttime.view.TaskListFragment;
import com.mooo.ewolvy.lasttime.viewmodel.TaskEditViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {
    void inject (TaskListFragment taskListFragment);
    void inject (TaskDetailFragment taskDetailFragment);

    Application application();
}
