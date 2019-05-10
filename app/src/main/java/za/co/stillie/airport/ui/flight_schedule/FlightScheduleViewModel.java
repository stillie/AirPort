package za.co.stillie.airport.ui.flight_schedule;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import javax.inject.Inject;

import za.co.stillie.airport.base.BaseViewModel;
import za.co.stillie.airport.enums.FlightStatus;
import za.co.stillie.airport.service.models.FlightScheduleResponse;

public class FlightScheduleViewModel extends BaseViewModel {

    private final FlightScheduleRepository mRepository;
    private FlightStatus mFlightStatus = FlightStatus.DEPARTURE;

    @Inject
    FlightScheduleViewModel(FlightScheduleRepository aRepository, LocalBroadcastManager aLocalBroadcastManager) {
        super(aLocalBroadcastManager);
        mRepository = aRepository;
    }

    String getFlightStatusTitle() {
        return mFlightStatus.getDisplayName();
    }

    int getFlightStatusDrawable() {
        return mFlightStatus.getFlightStatusDrawable();
    }

    void toggleFlightStatus() {
        mFlightStatus = mFlightStatus == FlightStatus.DEPARTURE ? FlightStatus.ARRIVAL : FlightStatus.DEPARTURE;
    }

    MutableLiveData<List<FlightScheduleResponse>> getFlightSchedule(String iataCode) {
        return mRepository.getFlightSchedule(iataCode, mFlightStatus);
    }
}
