package za.co.stillie.airport.di;

import android.app.Application;
import android.support.v4.content.LocalBroadcastManager;

import dagger.Module;
import dagger.Provides;
import za.co.stillie.airport.base.BaseRepository;
import za.co.stillie.airport.service.ApiService;
import za.co.stillie.airport.ui.map.MapRepository;

@Module
public class RepositoryModule {

    @Provides
    BaseRepository provideBaseRepository(Application aApplication, LocalBroadcastManager aLocalBroadcastManager) {
        return new BaseRepository(aLocalBroadcastManager, aApplication);
    }

    @Provides
    MapRepository provideMapRepository(Application aApplication, ApiService aApiService, LocalBroadcastManager aLocalBroadcastManager) {
        return new MapRepository(aApplication, aApiService, aLocalBroadcastManager);
    }

}
