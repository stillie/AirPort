package za.co.stillie.airport.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import pub.devrel.easypermissions.EasyPermissions;
import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseActivity;
import za.co.stillie.airport.base.BaseFragment;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.hasPermissions(this, BaseFragment.LOCATION_PERMISSIONS_LIST)) {
                goToHomeScreen();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.msg_request_permissions), BaseFragment.LOCATION_PERMISSIONS_CODE, BaseFragment.LOCATION_PERMISSIONS_LIST);
            }
        } else {
            goToHomeScreen();
        }
    }

    private void goToHomeScreen() {
        startActivity(HomeActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == BaseFragment.LOCATION_PERMISSIONS_CODE) {
            goToHomeScreen();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
