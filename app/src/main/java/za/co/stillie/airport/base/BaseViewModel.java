package za.co.stillie.airport.base;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import javax.inject.Inject;

import static za.co.stillie.airport.base.BaseRepository.BROADCAST_ERROR_MESSAGE;
import static za.co.stillie.airport.base.BaseRepository.INTENT_ERROR_MESSAGE;

public class BaseViewModel extends ViewModel {

    private LocalBroadcastManager mLocalBroadcastManager;

    @Inject
    public BaseViewModel(LocalBroadcastManager aLocalBroadcastManager) {
        mLocalBroadcastManager = aLocalBroadcastManager;
    }

    protected void sendErrorMessage(String aMessage) {
        Intent intent = new Intent(BROADCAST_ERROR_MESSAGE);
        intent.putExtra(INTENT_ERROR_MESSAGE, !TextUtils.isEmpty(aMessage) ? aMessage : "");
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
