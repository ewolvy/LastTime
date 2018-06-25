package com.mooo.ewolvy.lasttime.di;

import android.app.Application;

import com.mooo.ewolvy.lasttime.LastTimeApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final LastTimeApplication application;

    public ApplicationModule(LastTimeApplication application) {
        this.application = application;
    }

    @Provides
    LastTimeApplication provideLastTimeApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }
}
