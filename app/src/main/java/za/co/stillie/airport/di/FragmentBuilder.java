package za.co.stillie.airport.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import za.co.stillie.airport.base.BaseFragment;
import za.co.stillie.airport.ui.map.MapFragment;

@Module
public abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract BaseFragment baseFragment();

    @ContributesAndroidInjector
    abstract MapFragment mapFragment();


}
