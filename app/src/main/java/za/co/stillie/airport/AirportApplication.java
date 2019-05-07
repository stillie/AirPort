package za.co.stillie.airport;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;
import za.co.stillie.airport.di.AppComponent;
import za.co.stillie.airport.di.AppModule;
import za.co.stillie.airport.di.DaggerAppComponent;
import za.co.stillie.airport.di.RepositoryModule;

public class AirportApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent appComponent = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .repoModule(new RepositoryModule())
                .build();
        appComponent.inject(this);
        handleDebugLibraries();
    }


    private void handleDebugLibraries() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityDispatchingAndroidInjector;
    }
}
