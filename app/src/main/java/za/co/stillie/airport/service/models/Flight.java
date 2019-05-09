
package za.co.stillie.airport.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight {

    @SerializedName("number")

    private String number;
    @SerializedName("iataNumber")

    private String iataNumber;
    @SerializedName("icaoNumber")

    private String icaoNumber;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIataNumber() {
        return iataNumber;
    }

    public void setIataNumber(String iataNumber) {
        this.iataNumber = iataNumber;
    }

    public String getIcaoNumber() {
        return icaoNumber;
    }

    public void setIcaoNumber(String icaoNumber) {
        this.icaoNumber = icaoNumber;
    }

}
