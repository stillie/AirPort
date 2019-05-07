package za.co.stillie.airport.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import dagger.android.support.AndroidSupportInjection;
import za.co.stillie.airport.utils.LoggingHelper;

public class BaseFragment extends Fragment {
    protected static final int LOCATION_PERMISSIONS_CODE = 1234;
    protected static final String[] LOCATION_PERMISSIONS_LIST = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            AndroidSupportInjection.inject(this);
        } catch (IllegalArgumentException aE) {
            LoggingHelper.error("Please add " + this.getClass().getSimpleName() + " to FragmentBuilder.class", aE);
        }
        super.onCreate(savedInstanceState);

    }
}
