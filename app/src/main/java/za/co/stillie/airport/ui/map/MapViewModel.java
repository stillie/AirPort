package za.co.stillie.airport.ui.map;

import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseViewModel;

public class MapViewModel extends BaseViewModel implements OnMapReadyCallback {

    void onCreate(FragmentManager aFragmentManager) {
        SupportMapFragment mapFragment = (SupportMapFragment) aFragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap aGoogleMap) {

    }
}
