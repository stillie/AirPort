package za.co.stillie.airport.ui.flight_schedule;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;

import org.parceler.Parcels;

import javax.inject.Inject;

import za.co.stillie.airport.R;
import za.co.stillie.airport.base.BaseActivity;
import za.co.stillie.airport.databinding.ActivityFlightScheduleBinding;
import za.co.stillie.airport.di.MyViewModelFactory;
import za.co.stillie.airport.service.models.NearbyResponse;

public class FlightScheduleActivity extends BaseActivity {

    private final static String INTENT_NEARBY_AIRPORT = "INTENT_NEARBY_AIRPORT";
    @Inject
    MyViewModelFactory mFactory;
    private ActivityFlightScheduleBinding mBinding;
    private FlightScheduleViewModel mFlightScheduleViewModel;

    public static Intent getStartIntent(Context aContext, NearbyResponse aNearbyResponse) {
        Intent intent = new Intent(aContext, FlightScheduleActivity.class);
        intent.putExtra(INTENT_NEARBY_AIRPORT, Parcels.wrap(aNearbyResponse));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_flight_schedule);
        mBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        mFlightScheduleViewModel = ViewModelProviders.of(this, mFactory).get(FlightScheduleViewModel.class);
        setSupportActionBar(mBinding.toolbar);
        handleIntent();
    }

    private void handleIntent() {
        if (getIntent() != null) {
            NearbyResponse nearbyResponse = Parcels.unwrap(getIntent().getParcelableExtra(INTENT_NEARBY_AIRPORT));
            mBinding.setNearByAirport(nearbyResponse);
            setTitle(nearbyResponse.getNameAirport());
            FlightScheduleAdapter adapter = new FlightScheduleAdapter();
            mBinding.flightScheduleList.setAdapter(adapter);
            mBinding.flightScheduleList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mFlightScheduleViewModel.getFlightSchedule(nearbyResponse.getCodeIataAirport()).observe(this, aFlightScheduleResponses -> {
                if (aFlightScheduleResponses != null && !aFlightScheduleResponses.isEmpty()) {
                    adapter.addItems(aFlightScheduleResponses);
                }
            });
        }
    }
}
