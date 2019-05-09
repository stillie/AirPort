package za.co.stillie.airport.service.models;

import com.google.gson.annotations.SerializedName;

public class FlightScheduleResponse {

    @SerializedName("type")

    private String type;
    @SerializedName("status")

    private String status;
    @SerializedName("departure")

    private Departure departure;
    @SerializedName("arrival")

    private Arrival arrival;
    @SerializedName("airline")

    private Airline airline;
    @SerializedName("flight")

    private Flight flight;
    @SerializedName("codeshared")

    private Codeshared codeshared;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

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

    public Codeshared getCodeshared() {
        return codeshared;
    }

    public void setCodeshared(Codeshared codeshared) {
        this.codeshared = codeshared;
    }

}
