package br.com.esmocyp.cep.model;

/**
 * Created by ruhan on 23/04/17.
 *
 * This class represents a stream of geo-location smartphone data
 */
public class DoctorSensorData {

    /**
     * Smartphone UUID
     */
    private String idSmartphone;

    /**
     * Smartphone latitude
     */
    private double latitude;

    /**
     * Smartphone longitude
     */
    private double longitude;

    public String getIdSmartphone() {
        return idSmartphone;
    }

    public void setIdSmartphone(String idSmartphone) {
        this.idSmartphone = idSmartphone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
