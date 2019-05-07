package za.co.stillie.airport.ui.map;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.stillie.airport.base.BaseRepository;
import za.co.stillie.airport.service.ApiService;
import za.co.stillie.airport.service.models.NearbyResponse;

public class MapRepository extends BaseRepository {
    private final ApiService mApiService;

    @Inject
    public MapRepository(Application aApplication, ApiService aApiService, LocalBroadcastManager aLocalBroadcastManager) {
        super(aLocalBroadcastManager, aApplication);
        mApiService = aApiService;
    }

    public MutableLiveData<List<NearbyResponse>> getNearby(LatLng aLatLng, int distance) {

        final MutableLiveData<List<NearbyResponse>> mNearbyResponseMutableLiveData = new MutableLiveData<>();

        Call<Object> call = mApiService.executeNearByCall(aLatLng.latitude, aLatLng.longitude, distance);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.body() == null) return;
                String stringGson = new Gson().toJson(response.body());

                try {
                    Object responseObject = new JSONTokener(stringGson).nextValue();
                    if (responseObject instanceof JSONObject) {
                        handleJsonObjectResponse(response);
                    } else if (responseObject instanceof JSONArray) {
                        JSONArray jsonArray = new JSONArray(stringGson);
                        if (jsonArray.length() > 0) {
                            mNearbyResponseMutableLiveData.setValue(new Gson().fromJson(jsonArray.toString(), new TypeToken<List<NearbyResponse>>() {
                            }.getType()));
                        }
                    }
                } catch (JSONException e) {
                    sendGenericErrorMessage();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                sendErrorMessage(t.getLocalizedMessage());
            }
        });
        return mNearbyResponseMutableLiveData;
    }
}
