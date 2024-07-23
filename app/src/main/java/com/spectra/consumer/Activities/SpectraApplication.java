package com.spectra.consumer.Activities;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import activeandroid.ActiveAndroid;

public class SpectraApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
