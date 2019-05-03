package za.co.stillie.airport.ui.map;

import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import za.co.stillie.airport.base.BaseViewModel;

public class MapViewModel extends BaseViewModel implements OnMapReadyCallback {


    void onCreate(FragmentManager aFragmentManager) {
    }

    @Override
    public void onMapReady(GoogleMap aGoogleMap) {
    }

    private void moveToLocation(GoogleMap aGoogleMap) {
        // move camera to location
    }
}
