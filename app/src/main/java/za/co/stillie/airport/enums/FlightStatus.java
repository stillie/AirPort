package za.co.stillie.airport.enums;

import za.co.stillie.airport.R;

public enum FlightStatus {

    DEPARTURE("departure", "Departure", R.drawable.ic_baseline_flight_takeoff_24px),
    ARRIVAL("arrival", "Arrival", R.drawable.ic_baseline_flight_land_24px);

    private final String mFlightStatus;
    private final String mDisplayName;
    private final int mFlightStatusDrawable;

    FlightStatus(String aFlightStatus, String aDisplayName, int aFlightStatusDrawable) {
        mFlightStatus = aFlightStatus;
        mDisplayName = aDisplayName;
        mFlightStatusDrawable = aFlightStatusDrawable;
    }

    public String getFlightStatus() {
        return mFlightStatus;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public int getFlightStatusDrawable() {
        return mFlightStatusDrawable;
    }
}
