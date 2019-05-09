package za.co.stillie.airport.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import za.co.stillie.airport.BuildConfig;

public interface ApiService {
    @GET(BuildConfig.AVIATION_API_NEARBY_URL)
    Call<Object> executeNearByCall(@Query("lat") double lat, @Query("lng") double lng, @Query("distance") int distance);

    @GET(BuildConfig.AVIATION_API_SCHEDULE_URL)
    Call<Object> executeScheduleCall(@Query("iataCode") String aIata, @Query("type") String aType);

}
