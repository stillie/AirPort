package za.co.stillie.airport.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import za.co.stillie.airport.ui.flight_schedule.FlightScheduleViewModel;
import za.co.stillie.airport.ui.map.MapViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    public abstract ViewModelProvider.Factory bindsViewModelFactory(MyViewModelFactory aMyViewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel.class)
    public abstract ViewModel provideMapViewModel(MapViewModel aMapViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FlightScheduleViewModel.class)
    public abstract ViewModel provideFlightScheduleViewModel(FlightScheduleViewModel aFlightScheduleViewModel);

}
