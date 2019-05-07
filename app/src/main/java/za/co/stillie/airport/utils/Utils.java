package za.co.stillie.airport.utils;

import android.app.Application;

import javax.inject.Inject;

public class Utils {
    private final Application mApplication;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public Utils(Application aApplication, PreferencesHelper aPreferencesHelper) {
        mApplication = aApplication;
        mPreferencesHelper = aPreferencesHelper;
    }
}
