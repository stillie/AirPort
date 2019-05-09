package za.co.stillie.airport.ui.map;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import za.co.stillie.airport.base.BaseFragment;
import za.co.stillie.airport.databinding.FragmentMapBinding;
import za.co.stillie.airport.di.MyViewModelFactory;
import za.co.stillie.airport.service.models.NearbyResponse;
import za.co.stillie.airport.ui.flight_schedule.FlightScheduleActivity;

public class MapFragment extends BaseFragment implements MapViewModel.MarkerOnClick {

    @Inject
    MyViewModelFactory mFactory;
    private MapViewModel mMapViewModel;
    private FragmentMapBinding mFragmentMapBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapViewModel = ViewModelProviders.of(this, mFactory).get(MapViewModel.class);
    }

    @Override
    public void onMarkerClicked(NearbyResponse aNearbyResponse) {
        startActivity(FlightScheduleActivity.getStartIntent(getContext(), aNearbyResponse));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == BaseFragment.LOCATION_PERMISSIONS_CODE) {
            mMapViewModel.initMap(getBaseActivity(), mFragmentMapBinding.mapView);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMapBinding = FragmentMapBinding.inflate(getLayoutInflater());
        mMapViewModel.initMap(getBaseActivity(), mFragmentMapBinding.mapView);
        mMapViewModel.setMarkerOnClick(this);
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
}
