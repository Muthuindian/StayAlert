package com.tech42labs.stayalert.dagger;

import android.content.Context;

import com.tech42labs.stayalert.application.StayAlertApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mari on 3/10/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent  {

    void inject(StayAlertApplication application);

    void inject(Context context);
}
