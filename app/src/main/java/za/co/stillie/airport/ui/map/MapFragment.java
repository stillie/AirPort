package za.co.stillie.airport.ui.map;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseFragment;

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapViewModel mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap aGoogleMap) {

    }
}
