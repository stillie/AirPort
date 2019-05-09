package za.co.stillie.airport.base;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import javax.inject.Inject;

import za.co.stillie.airport.BuildConfig;
import za.co.stillie.airport.R;
import za.co.stillie.airport.service.models.ErrorResponse;

public class BaseRepository {

    public static final String BROADCAST_ERROR_MESSAGE = BuildConfig.APPLICATION_ID + ".BROADCAST_ERROR_MESSAGE";
    public static final String INTENT_ERROR_MESSAGE = "INTENT_ERROR_MESSAGE";

    private final LocalBroadcastManager mLocalBroadcastManager;
    private final Application mApplication;
    private final MutableLiveData<String> mErrorMutableLiveData = new MutableLiveData<>();

    @Inject
    public BaseRepository(LocalBroadcastManager aLocalBroadcastManager, Application aApplication) {
        mLocalBroadcastManager = aLocalBroadcastManager;
        mApplication = aApplication;
        Observer<String> observer = this::sendErrorMessage;
        mErrorMutableLiveData.observeForever(observer);
    }

    protected void handleJsonObjectResponse(ErrorResponse aResponse) {
        handleErrorResponse(aResponse);
    }

    private void handleErrorResponse(ErrorResponse baseResponseModel) {
        if (baseResponseModel == null || baseResponseModel.getError() == null) {
            sendGenericErrorMessage();
            return;
        }
        if (baseResponseModel.getError() != null)
            sendErrorMessage(baseResponseModel.getError().getText());
        else
            sendGenericErrorMessage();
    }

    protected void sendErrorMessage(String aMessage) {
        Intent intent = new Intent(BROADCAST_ERROR_MESSAGE);
        intent.putExtra(INTENT_ERROR_MESSAGE, !TextUtils.isEmpty(aMessage) ? aMessage : "");
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    protected void sendGenericErrorMessage() {
        mErrorMutableLiveData.setValue(mApplication.getString(R.string.generic_error_message));
    }
}
