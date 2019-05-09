package za.co.stillie.airport.ui.flight_schedule;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import za.co.stillie.airport.base.BaseViewModel;
import za.co.stillie.airport.service.models.FlightScheduleResponse;

public class FlightScheduleViewModel extends BaseViewModel {

    private final FlightScheduleRepository mRepository;

    @Inject
    public FlightScheduleViewModel(FlightScheduleRepository aRepository) {
        mRepository = aRepository;
    }

    public MutableLiveData<List<FlightScheduleResponse>> getFlightSchedule(String iataCode) {
        return mRepository.getFlightSchedule(iataCode, "departure");
    }
}
