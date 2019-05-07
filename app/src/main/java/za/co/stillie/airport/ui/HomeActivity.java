package za.co.stillie.airport.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseActivity;
import za.co.stillie.airport.databinding.ActivityHomeBinding;
import za.co.stillie.airport.ui.map.MapFragment;

public class HomeActivity extends BaseActivity {


    private ActivityHomeBinding mActivityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        addFragment("Nearby Airports", mActivityHomeBinding.mapContainer.getId(), new MapFragment(), true);
    }

}
