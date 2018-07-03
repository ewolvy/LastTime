package com.mooo.ewolvy.lasttime.di;

import android.app.Application;

import com.mooo.ewolvy.lasttime.view.ListFragment;
import com.mooo.ewolvy.lasttime.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {
    void inject (ListFragment listFragment);

    Application application();
}
