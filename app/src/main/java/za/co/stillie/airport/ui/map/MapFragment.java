package za.co.stillie.airport.ui.map;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseFragment;
import za.co.stillie.airport.databinding.FragmentMapBinding;
import za.co.stillie.airport.di.MyViewModelFactory;

public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    MyViewModelFactory mFactory;

    private MutableLiveData<LatLng> mCurrentLatLng = new MutableLiveData<>();
    private GoogleMap mGoogleMap;
    private MapViewModel mMapViewModel;
    private FragmentMapBinding mFragmentMapBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapViewModel = ViewModelProviders.of(this, mFactory).get(MapViewModel.class);
        mCurrentLatLng.observe(this, this::centerMap);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMapBinding = FragmentMapBinding.inflate(getLayoutInflater());
        mFragmentMapBinding.mapView.getMapAsync(this);
        mFragmentMapBinding.mapView.onCreate(savedInstanceState);
        return mFragmentMapBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFragmentMapBinding.mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentMapBinding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentMapBinding.mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentMapBinding.mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentMapBinding.mapView.onDestroy();
    }

    @AfterPermissionGranted(LOCATION_PERMISSIONS_CODE)
    private void getCurrentLocation() {
        if (EasyPermissions.hasPermissions(requireContext(), LOCATION_PERMISSIONS_LIST)) {

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
            try {

                mFusedLocationClient.getLastLocation().addOnSuccessListener(getBaseActivity(), location -> {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mCurrentLatLng.setValue(latLng);
                    } else {
//                        displayDialog(getString(R.string.msg_location_error));
                    }
                });

            } catch (SecurityException sec) {
//                displayDialog(sec.getMessage());
            }
        } else {
            EasyPermissions.requestPermissions(requireActivity(), getString(R.string.msg_request_permissions), LOCATION_PERMISSIONS_CODE, LOCATION_PERMISSIONS_LIST);
        }
    }

    private void centerMap(LatLng aLatLng) {
        mGoogleMap.clear();
        try {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(aLatLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);

            mMapViewModel.getNearbyResponse(aLatLng, 100).observe(this, aNearbyResponses -> mMapViewModel.plotAirportsOnMap(aNearbyResponses, mGoogleMap));

            mGoogleMap.setOnMarkerClickListener(this);

        } catch (SecurityException sec) {
//            displayDialog(sec.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap aGoogleMap) {
        mGoogleMap = aGoogleMap;
        getCurrentLocation();
    }

    @Override
    public boolean onMarkerClick(Marker aMarker) {
        return false;
    }
}
