package za.co.stillie.airport.service.models;

import com.google.gson.annotations.SerializedName;

public class Arrival {

    @SerializedName("iataCode")

    private String iataCode;
    @SerializedName("icaoCode")

    private String icaoCode;
    @SerializedName("baggage")

    private String baggage;
    @SerializedName("scheduledTime")

    private String scheduledTime;
    @SerializedName("estimatedTime")

    private String estimatedTime;
    @SerializedName("actualTime")

    private String actualTime;

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

}
