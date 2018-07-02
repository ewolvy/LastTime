package com.mooo.ewolvy.lasttime.di;

import android.app.Application;

import com.mooo.ewolvy.lasttime.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {
    void inject (MainActivity mainActivity);

    Application application();
}
