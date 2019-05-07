package za.co.stillie.airport.di;

import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import za.co.stillie.airport.BuildConfig;

public class CustomInterceptor implements Interceptor {

    private final LocalBroadcastManager mLocalBroadcastManager;

    public CustomInterceptor(LocalBroadcastManager aLocalBroadcastManager) {
        mLocalBroadcastManager = aLocalBroadcastManager;
    }

    /**
     * Intercepts the request, and builds it, adding headers and a base url
     *
     * @param aChain a Chain
     * @return a OkHTT 3 Response
     */
    @Override
    public Response intercept(@NonNull Chain aChain) throws IOException {
        Request request = aChain.request();
        HttpUrl httpUrl = HttpUrl.parse(BuildConfig.BASE_URL);
        if (httpUrl != null && httpUrl.host() != null) {
            HttpUrl newUrl = request.url().newBuilder()
                    .scheme("http")
                    .host(httpUrl.host())
                    .build();
            Request.Builder requestBuilder = setup(request, newUrl);
            request = requestBuilder.build();
        }

        Response response = aChain.proceed(request);

        String stringGson = new Gson().toJson(response.body());

        try {
            Object responseObject = new JSONTokener(stringGson).nextValue();
            if (responseObject instanceof JSONObject) {

//                handleJSONObjectResponse(response, stringGson);

            } else if (responseObject instanceof JSONArray) {

//                if (handleJSONArrayResponse(response, stringGson) != null && handleJSONArrayResponse(response, stringGson).size() > 0) {
//                    mMutableLiveData.setValue(handleJSONArrayResponse(response, stringGson));
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return aChain.proceed(request);
    }


    /**
     * Builds a request based on the url provided - which includes adding our common HTTP headers
     *
     * @param aRequest the origin request
     * @param aNewUrl  a url to form the baseUrl
     * @return a request builder
     */
    private Request.Builder setup(Request aRequest, HttpUrl aNewUrl) {
        return aRequest.newBuilder()
                .header("Accept", "application/json")
                .url(aNewUrl);
    }
}
