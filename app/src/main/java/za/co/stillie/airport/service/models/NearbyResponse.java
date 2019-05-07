package za.co.stillie.airport.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearbyResponse {

    @SerializedName("nameAirport")
    @Expose
    private String mNameAirport;
    @SerializedName("codeIataAirport")
    @Expose
    private String mCodeIataAirport;
    @SerializedName("codeIcaoAirport")
    @Expose
    private String mCodeIcaoAirport;
    @SerializedName("nameTranslations")
    @Expose
    private String mNameTranslations;
    @SerializedName("latitudeAirport")
    @Expose
    private String mLatitudeAirport;
    @SerializedName("longitudeAirport")
    @Expose
    private String mLongitudeAirport;
    @SerializedName("timezone")
    @Expose
    private String mTimezone;
    @SerializedName("GMT")
    @Expose
    private String mGmt;
    @SerializedName("phone")
    @Expose
    private String mPhone;
    @SerializedName("nameCountry")
    @Expose
    private String mNameCountry;
    @SerializedName("codeIso2Country")
    @Expose
    private String mCodeIso2Country;
    @SerializedName("codeIataCity")
    @Expose
    private String mCodeIataCity;
    @SerializedName("distance")
    @Expose
    private String mDistance;

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

    public String getLatitudeAirport() {
        return mLatitudeAirport;
    }

    public void setLatitudeAirport(String aLatitudeAirport) {
        mLatitudeAirport = aLatitudeAirport;
    }

    public String getLongitudeAirport() {
        return mLongitudeAirport;
    }

    public void setLongitudeAirport(String aLongitudeAirport) {
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
