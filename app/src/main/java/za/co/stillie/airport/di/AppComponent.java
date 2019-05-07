package za.co.stillie.airport.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import za.co.stillie.airport.AirportApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        RepositoryModule.class,
        FragmentBuilder.class,
        ViewModelModule.class,
})

public interface AppComponent {

    void inject(AirportApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(AirportApplication application);

        Builder repoModule(RepositoryModule aRepositoryModule);

        Builder appModule(AppModule appModule);

        AppComponent build();
    }

}
