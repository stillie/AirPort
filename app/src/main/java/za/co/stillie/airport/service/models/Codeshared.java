package za.co.stillie.airport.service.models;

import com.google.gson.annotations.SerializedName;

public class Codeshared {

    @SerializedName("airline")

    private Airline airline;
    @SerializedName("flight")

    private Flight flight;

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
