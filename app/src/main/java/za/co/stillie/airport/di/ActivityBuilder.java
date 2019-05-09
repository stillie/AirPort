package za.co.stillie.airport.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import za.co.stillie.airport.ui.HomeActivity;
import za.co.stillie.airport.ui.SplashActivity;
import za.co.stillie.airport.ui.flight_schedule.FlightScheduleActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract FlightScheduleActivity bindFlightScheduleActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract SplashActivity bindSplashActivity();
}