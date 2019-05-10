package za.co.stillie.airport.ui.flight_schedule;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    private FlightScheduleAdapter mAdapter;
    private ActivityFlightScheduleBinding mBinding;
    private FlightScheduleViewModel mFlightScheduleViewModel;
    private NearbyResponse mNearbyResponse;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            MenuItem flightStatusMenuItem = menu.findItem(R.id.flightState);
            if (flightStatusMenuItem != null) {
                flightStatusMenuItem.setIcon(ContextCompat.getDrawable(getApplicationContext(), mFlightScheduleViewModel.getFlightStatusDrawable()));
                flightStatusMenuItem.setTitle(mFlightScheduleViewModel.getFlightStatusTitle());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.flightState:
                mFlightScheduleViewModel.toggleFlightStatus();
                doFlightScheduleCall();
                supportInvalidateOptionsMenu();
                return true;

            case R.id.navigateToAirport:
                showNavigateDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNavigateDialog() {
        displayDialog(
                getString(R.string.lbl_navigate_to_airport),
                getString(R.string.lbl_OK),
                (dialog, which) -> {
                    String location = mNearbyResponse.getLatitudeAirport() + "," + mNearbyResponse.getLongitudeAirport();
                    Uri uri = Uri.parse("google.navigation:q=" + location);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                },
                getString(R.string.lbl_Cancel),
                (dialog, which) -> {
                    // do nothing
                },
                true);
    }

    private void doFlightScheduleCall() {
        mFlightScheduleViewModel.getFlightSchedule(mNearbyResponse.getCodeIataAirport()).observe(this, aFlightScheduleResponses -> {
            if (aFlightScheduleResponses != null && !aFlightScheduleResponses.isEmpty()) {
                mAdapter.addItems(aFlightScheduleResponses);
            }
        });
    }

    private void handleIntent() {
        if (getIntent() != null) {
            mNearbyResponse = Parcels.unwrap(getIntent().getParcelableExtra(INTENT_NEARBY_AIRPORT));
            mBinding.setNearByAirport(mNearbyResponse);
            setTitle(mNearbyResponse.getNameAirport());
            mAdapter = new FlightScheduleAdapter();
            mBinding.flightScheduleList.setAdapter(mAdapter);
            mBinding.flightScheduleList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            doFlightScheduleCall();
        }
    }
}
