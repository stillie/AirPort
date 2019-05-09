package za.co.stillie.airport.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@SuppressWarnings("WeakerAccess")
@Parcel
public class NearbyResponse {

    @SerializedName("nameAirport")

    String mNameAirport;
    @SerializedName("codeIataAirport")

    String mCodeIataAirport;
    @SerializedName("codeIcaoAirport")

    String mCodeIcaoAirport;
    @SerializedName("nameTranslations")

    String mNameTranslations;
    @SerializedName("latitudeAirport")

    double mLatitudeAirport;
    @SerializedName("longitudeAirport")

    double mLongitudeAirport;
    @SerializedName("timezone")

    String mTimezone;
    @SerializedName("GMT")

    String mGmt;
    @SerializedName("phone")

    String mPhone;
    @SerializedName("nameCountry")

    String mNameCountry;
    @SerializedName("codeIso2Country")

    String mCodeIso2Country;
    @SerializedName("codeIataCity")

    String mCodeIataCity;
    @SerializedName("distance")

    String mDistance;

    public String getNameAirport() {
        return mNameAirport;
    }

    public void setNameAirport(String aNameAirport) {
        mNameAirport = aNameAirport;
    }

    public String getCodeIataAirport() {
        return mCodeIataAirport;
    }

    public void setCodeIataAirport(String aCodeIataAirport) {
        mCodeIataAirport = aCodeIataAirport;
    }

    public String getCodeIcaoAirport() {
        return mCodeIcaoAirport;
    }

    public void setCodeIcaoAirport(String aCodeIcaoAirport) {
        mCodeIcaoAirport = aCodeIcaoAirport;
    }

    public String getNameTranslations() {
        return mNameTranslations;
    }

    public void setNameTranslations(String aNameTranslations) {
        mNameTranslations = aNameTranslations;
    }

    public double getLatitudeAirport() {
        return mLatitudeAirport;
    }

    public void setLatitudeAirport(double aLatitudeAirport) {
        mLatitudeAirport = aLatitudeAirport;
    }

    public double getLongitudeAirport() {
        return mLongitudeAirport;
    }

    public void setLongitudeAirport(double aLongitudeAirport) {
        mLongitudeAirport = aLongitudeAirport;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String aTimezone) {
        mTimezone = aTimezone;
    }

    public String getGmt() {
        return mGmt;
    }

    public void setGmt(String aGmt) {
        mGmt = aGmt;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String aPhone) {
        mPhone = aPhone;
    }

    public String getNameCountry() {
        return mNameCountry;
    }

    public void setNameCountry(String aNameCountry) {
        mNameCountry = aNameCountry;
    }

    public String getCodeIso2Country() {
        return mCodeIso2Country;
    }

    public void setCodeIso2Country(String aCodeIso2Country) {
        mCodeIso2Country = aCodeIso2Country;
    }

    public String getCodeIataCity() {
        return mCodeIataCity;
    }

    public void setCodeIataCity(String aCodeIataCity) {
        mCodeIataCity = aCodeIataCity;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String aDistance) {
        mDistance = aDistance;
    }

    @Override
    public String toString() {
        return "NearbyResponse{" +
                "mNameAirport='" + mNameAirport + '\'' +
                ", mCodeIataAirport='" + mCodeIataAirport + '\'' +
                ", mCodeIcaoAirport='" + mCodeIcaoAirport + '\'' +
                ", mNameTranslations='" + mNameTranslations + '\'' +
                ", mLatitudeAirport='" + mLatitudeAirport + '\'' +
                ", mLongitudeAirport='" + mLongitudeAirport + '\'' +
                ", mTimezone='" + mTimezone + '\'' +
                ", mGmt='" + mGmt + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mNameCountry='" + mNameCountry + '\'' +
                ", mCodeIso2Country='" + mCodeIso2Country + '\'' +
                ", mCodeIataCity='" + mCodeIataCity + '\'' +
                ", mDistance='" + mDistance + '\'' +
                '}';
    }
}
