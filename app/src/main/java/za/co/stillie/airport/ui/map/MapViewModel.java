package za.co.stillie.airport.ui.map;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseViewModel;
import za.co.stillie.airport.service.models.NearbyResponse;
import za.co.stillie.airport.utils.LoggingHelper;

public class MapViewModel extends BaseViewModel {

    private final Application mApplication;
    private final MapRepository mMapRepository;


    @Inject
    public MapViewModel(Application aApplication, MapRepository aMapRepository) {
        mApplication = aApplication;
        mMapRepository = aMapRepository;
    }

    /**
     * @param drawable drawable to use a mp marker
     * @return Bitmap descriptor of icon
     */
    public static BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public MutableLiveData<List<NearbyResponse>> getNearbyResponse(LatLng aLatLng, int aDistance) {
        return mMapRepository.getNearby(aLatLng, aDistance);
    }

    void plotAirportsOnMap(List<NearbyResponse> airportModelList, GoogleMap aGoogleMap) {

        List<NearbyResponse> airportPlotPoint = new ArrayList<>();

        for (NearbyResponse aNearbyAirportModel : airportModelList) {
            if (!TextUtils.isEmpty(aNearbyAirportModel.getLatitudeAirport())
                    && Double.parseDouble(aNearbyAirportModel.getLatitudeAirport()) != 0
                    && !TextUtils.isEmpty(aNearbyAirportModel.getLongitudeAirport())
                    && Double.parseDouble(aNearbyAirportModel.getLongitudeAirport()) != 0) {
                airportPlotPoint.add(aNearbyAirportModel);
            }
        }

        if (airportPlotPoint.size() > 0) {
            plotAirportPoint(airportPlotPoint, aGoogleMap);
        }
    }

    private void plotAirportPoint(List<NearbyResponse> airportPlotPointList, GoogleMap aGoogleMap) {
        if (aGoogleMap != null) {

            try {
                aGoogleMap.clear();
                aGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                ArrayList<Marker> markers = new ArrayList<>();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                Drawable mapIcon = mApplication.getResources().getDrawable(R.drawable.ic_airport_location);
                BitmapDescriptor marker_Icon = getMarkerIconFromDrawable(mapIcon);

                for (NearbyResponse aNearbyAirportModel : airportPlotPointList) {
                    LatLng airportLatLng = new LatLng(Double.parseDouble(aNearbyAirportModel.getLatitudeAirport()), Double.parseDouble(aNearbyAirportModel.getLongitudeAirport()));
                    MarkerOptions markerOptions = new MarkerOptions().position(airportLatLng);
                    markerOptions.icon(marker_Icon);
                    markerOptions.flat(true);
                    builder.include(airportLatLng);
                    markers.add(aGoogleMap.addMarker(markerOptions));
                }
                moveCameraToShowMarkers(aGoogleMap, builder.build());
            } catch (Exception ex) {
                LoggingHelper.error("Plot airport error", ex);
            }
        }
    }

    private void moveCameraToShowMarkers(GoogleMap map, LatLngBounds bounds) {
        int width = mApplication.getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.20);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }
}
