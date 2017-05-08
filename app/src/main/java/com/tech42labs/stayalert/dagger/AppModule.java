package com.tech42labs.stayalert.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.tech42labs.stayalert.application.StayAlertApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mari on 3/10/17.
 */

@Module
public class AppModule {

    private final StayAlertApplication application;

    public AppModule(StayAlertApplication trackingApplication) {
        this.application = trackingApplication;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context)
    {
        return context.getSharedPreferences("My_Prefs" , Context.MODE_PRIVATE);
    }
}
