package za.co.stillie.airport.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import za.co.stillie.airport.R;
import za.co.stillie.airport.utils.LoggingHelper;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements HasActivityInjector, HasSupportFragmentInjector {
    private final BroadcastReceiver mDialogBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String filter = intent.getAction();
            if (TextUtils.isEmpty(filter)) {
                return;
            }
            switch (filter) {
                case BaseRepository.BROADCAST_ERROR_MESSAGE:
                    displayDialog(intent.getStringExtra(BaseRepository.INTENT_ERROR_MESSAGE));
                    break;
            }
        }
    };
    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjectorActivity;
    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjectorFragment;

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDialogBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mDialogBroadcastReceiver, getIntentFilters());
    }

    private IntentFilter getIntentFilters() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BaseRepository.BROADCAST_ERROR_MESSAGE);
        return intentFilter;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjectorActivity;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjectorFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            AndroidInjection.inject(this);
        } catch (IllegalArgumentException aE) {
            LoggingHelper.error("Please add " + this.getClass().getSimpleName() + " to ActivityBuilder.class", aE);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDialogBroadcastReceiver);
    }


    /**
     * Displays a dialog with no actions
     *
     * @param aMessage - Message to display
     */
    public void displayDialog(String aMessage) {
        displayDialog(aMessage, getString(R.string.lbl_OK), null, null, null, true);
    }

    /**
     * Method to display a dialog with both positive and negative actions
     *
     * @param aMessage         - Message to display
     * @param aPositiveText    - Positive Text eg YES / Okay
     * @param aPositiveOnClick - Positive on click action
     * @param aNegativeOnClick - Negative on click action
     * @param aNegativeText    - Negative text eg NO / Cancel
     * @param cancellable      - Set the dialog to be cancellable when tapping outside the dialog
     */
    public void displayDialog(String aMessage, String aPositiveText, DialogInterface.OnClickListener aPositiveOnClick, String aNegativeText, DialogInterface.OnClickListener aNegativeOnClick, boolean cancellable) {
        if (TextUtils.isEmpty(aMessage)) {
            aMessage = getString(R.string.generic_error_message);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(aMessage);
        if (!TextUtils.isEmpty(aNegativeText)) {
            if (aNegativeOnClick == null) {
                aNegativeOnClick = (dialog, which) -> {
                    // Do nothing
                };
            }
            builder.setNegativeButton(aNegativeText, aNegativeOnClick);
        }
        if (!TextUtils.isEmpty(aPositiveText)) {
            builder.setPositiveButton(aPositiveText, aPositiveOnClick);
        }
        builder.setCancelable(cancellable);
        AlertDialog alert = builder.create();
        alert.show();
        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.BLACK);
        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.BLACK);
    }

    public void addFragment(String title, int containerId, Fragment fragment, boolean keepOnBackState) {

        setTitle(title);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (!keepOnBackState) {
            int count = manager.getBackStackEntryCount();
            for (int i = 0; i < count; ++i) {
                manager.popBackStack();
            }
        }
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        if (keepOnBackState) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }
}
