package za.co.stillie.airport.ui.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseViewModel;
import za.co.stillie.airport.service.models.NearbyResponse;
import za.co.stillie.airport.utils.LoggingHelper;

import static za.co.stillie.airport.base.BaseFragment.LOCATION_PERMISSIONS_CODE;
import static za.co.stillie.airport.base.BaseFragment.LOCATION_PERMISSIONS_LIST;

public class MapViewModel extends BaseViewModel implements GoogleMap.OnMarkerClickListener {

    private final Application mApplication;
    private final MapRepository mMapRepository;
    @SuppressLint("StaticFieldLeak")
    private Activity mActivity;
    private List<NearbyResponse> mNearbyAirportsList;
    private GoogleMap mGoogleMap;
    private final Observer<List<NearbyResponse>> mListObserver = new Observer<List<NearbyResponse>>() {
        @Override
        public void onChanged(@Nullable List<NearbyResponse> aNearbyResponses) {
            if (aNearbyResponses != null && !aNearbyResponses.isEmpty()) {
                mNearbyAirportsList = aNearbyResponses;
                plotAirportsOnMap(mNearbyAirportsList);
            }
        }
    };
    private MutableLiveData<LatLng> mCurrentLatLng = new MutableLiveData<>();
    private MarkerOnClick mMarkerOnClick;

    @Inject
    MapViewModel(Application aApplication, MapRepository aMapRepository, LocalBroadcastManager aLocalBroadcastManager) {
        super(aLocalBroadcastManager);
        mApplication = aApplication;
        mMapRepository = aMapRepository;
        mCurrentLatLng.observeForever(aLatLng -> {
            if (aLatLng == null) return;
            centerMap(aLatLng);
        });
    }

    private static BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void centerMap(LatLng aLatLng) {
        mGoogleMap.clear();
        try {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(aLatLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);

            getNearbyResponse(aLatLng).observeForever(mListObserver);

        } catch (SecurityException sec) {
            sendErrorMessage(sec.getMessage());
        }
    }

    void initMap(Activity aActivity, MapView aMapView) {
        mActivity = aActivity;
        aMapView.getMapAsync(aGoogleMap -> {
            mGoogleMap = aGoogleMap;
            getCurrentLocation();
        });
    }

    @AfterPermissionGranted(LOCATION_PERMISSIONS_CODE)
    private void getCurrentLocation() {
        if (EasyPermissions.hasPermissions(mActivity, LOCATION_PERMISSIONS_LIST)) {

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);
            try {

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(mActivity, location -> {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mCurrentLatLng.setValue(latLng);
                    } else {
                        sendErrorMessage(mApplication.getString(R.string.msg_location_error));
                    }
                });

            } catch (SecurityException sec) {
                sendErrorMessage(sec.getMessage());
            }
        } else {
            EasyPermissions.requestPermissions(mActivity, mApplication.getString(R.string.msg_request_permissions), LOCATION_PERMISSIONS_CODE, LOCATION_PERMISSIONS_LIST);
        }
    }

    private MutableLiveData<List<NearbyResponse>> getNearbyResponse(LatLng aLatLng) {
        return mMapRepository.getNearby(aLatLng, 100);
    }

    private void plotAirportsOnMap(List<NearbyResponse> airportModelList) {

        mGoogleMap.setOnMarkerClickListener(this);

        List<NearbyResponse> airportPlotPoint = new ArrayList<>(airportModelList);

        if (airportPlotPoint.size() > 0) {
            plotAirportPoint(airportPlotPoint);
        }
    }

    void setMarkerOnClick(MarkerOnClick aMarkerOnClick) {
        mMarkerOnClick = aMarkerOnClick;
    }

    private void plotAirportPoint(List<NearbyResponse> airportPlotPointList) {
        if (mGoogleMap != null) {
            try {
                mGoogleMap.clear();
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                Drawable mapIcon = mApplication.getResources().getDrawable(R.drawable.ic_pin);
                BitmapDescriptor marker_Icon = getMarkerIconFromDrawable(mapIcon);
                for (NearbyResponse aNearbyAirportModel : airportPlotPointList) {
                    LatLng airportLatLng = new LatLng(aNearbyAirportModel.getLatitudeAirport(), aNearbyAirportModel.getLongitudeAirport());
                    MarkerOptions markerOptions = new MarkerOptions().position(airportLatLng);
                    markerOptions.title(aNearbyAirportModel.getNameAirport());
                    markerOptions.icon(marker_Icon);
                    markerOptions.flat(true);
                    mGoogleMap.addMarker(markerOptions);
                    builder.include(airportLatLng);
                }
                moveCameraToShowMarkers(builder.build());
            } catch (Exception ex) {
                LoggingHelper.error("Plot airport error", ex);
            }
        }
    }

    private void moveCameraToShowMarkers(LatLngBounds bounds) {
        int width = mApplication.getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.20);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }

    @Nullable
    private NearbyResponse getNearbyAirportByCoords(Marker aMarker) {
        for (NearbyResponse nearbyAirport : mNearbyAirportsList) {
            if (doesMarkerMatchNearbyAirport(aMarker, nearbyAirport)) {
                return nearbyAirport;
            }
        }
        return null;
    }

    private boolean doesMarkerMatchNearbyAirport(Marker aMarker, NearbyResponse aNearbyResponse) {
        return aMarker.getPosition().latitude == aNearbyResponse.getLatitudeAirport() && aMarker.getPosition().longitude == aNearbyResponse.getLongitudeAirport();
    }

    @Override
    public boolean onMarkerClick(Marker aMarker) {
        NearbyResponse nearbyResponse = getNearbyAirportByCoords(aMarker);
        if (nearbyResponse != null) {
            mMarkerOnClick.onMarkerClicked(nearbyResponse);
        }
        return false;
    }

    public interface MarkerOnClick {
        void onMarkerClicked(NearbyResponse aNearbyResponse);
    }
}
