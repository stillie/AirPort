package za.co.stillie.airport.ui.flight_schedule;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.v4.content.LocalBroadcastManager;

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
import za.co.stillie.airport.enums.FlightStatus;
import za.co.stillie.airport.service.ApiService;
import za.co.stillie.airport.service.models.ErrorResponse;
import za.co.stillie.airport.service.models.FlightScheduleResponse;

public class FlightScheduleRepository extends BaseRepository {

    private final ApiService mApiService;

    @Inject
    public FlightScheduleRepository(Application aApplication, LocalBroadcastManager aLocalBroadcastManager, ApiService aApiService) {
        super(aLocalBroadcastManager, aApplication);
        mApiService = aApiService;
    }

    MutableLiveData<List<FlightScheduleResponse>> getFlightSchedule(String aIataCode, FlightStatus aType) {

        final MutableLiveData<List<FlightScheduleResponse>> mutableLiveData = new MutableLiveData<>();

        showProgress();
        Call<Object> call = mApiService.executeScheduleCall(aIataCode, aType.getFlightStatus());

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                hideProgress();
                try {
                    String jsonObject = new Gson().toJson(response.body());
                    Object responseObject = new JSONTokener(jsonObject).nextValue();
                    if (responseObject instanceof JSONObject) {
                        handleJsonObjectResponse(new Gson().fromJson(jsonObject, ErrorResponse.class));
                    } else if (responseObject instanceof JSONArray) {
                        JSONArray jsonArray = new JSONArray(jsonObject);
                        if (jsonArray.length() > 0) {
                            mutableLiveData.setValue(new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FlightScheduleResponse>>() {
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
                hideProgress();
            }
        });
        return mutableLiveData;
    }
}
