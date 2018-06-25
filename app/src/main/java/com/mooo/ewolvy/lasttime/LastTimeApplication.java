package com.mooo.ewolvy.lasttime;

import android.app.Application;

import com.mooo.ewolvy.lasttime.di.ApplicationComponent;
import com.mooo.ewolvy.lasttime.di.ApplicationModule;
import com.mooo.ewolvy.lasttime.di.RoomModule;

public class LastTimeApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
