package za.co.stillie.airport.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import za.co.stillie.airport.ui.HomeActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract HomeActivity bindHomeActivity();
}