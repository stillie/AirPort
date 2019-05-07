package za.co.stillie.airport.di;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import za.co.stillie.airport.BuildConfig;
import za.co.stillie.airport.service.ApiService;
import za.co.stillie.airport.utils.LoggingHelper;
import za.co.stillie.airport.utils.PreferencesHelper;
import za.co.stillie.airport.utils.Utils;

@Module(includes = ViewModelModule.class)
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    SSLSocketFactory getTrustAllSocketFactory() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            return null;
        }
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    ApiService providesApiService(Retrofit aRetrofit) {
        return aRetrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    Retrofit provideNetwork(OkHttpClient aOkHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(aOkHttpClient)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient(CustomInterceptor aCustomInterceptor, SSLSocketFactory aSSLSocketFactory) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (aSSLSocketFactory != null) {
            httpClient.sslSocketFactory(aSSLSocketFactory);
        } else {
            LoggingHelper.error("SSL null");
        }
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
            httpClient.addNetworkInterceptor(new StethoInterceptor());
        }
        httpClient.addInterceptor(aCustomInterceptor);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        return httpClient.build();
    }

    /**
     * Provides a single instance of our SharedPrefs utility class
     *
     * @return SharedPreferencesUtils
     */
    @Singleton
    @Provides
    PreferencesHelper providesSharedPreferencesUtility() {
        return new PreferencesHelper(application);
    }

    /**
     * Provides the Application Context
     *
     * @return a Context
     */
    @Provides
    Context providesApplicationContext() {
        return application;
    }

    /**
     * Provides a single instance of the LocalBroadcastManager
     *
     * @return LocalBroadcastManager
     */
    @Provides
    LocalBroadcastManager providesLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(application);
    }

    @Provides
    @Singleton
    CustomInterceptor provideInterceptor(LocalBroadcastManager aLocalBroadcastManager) {
        return new CustomInterceptor(aLocalBroadcastManager);
    }

    @Provides
    @Singleton
    Utils providesUtils(PreferencesHelper aPreferencesHelper) {
        return new Utils(application, aPreferencesHelper);
    }
}




