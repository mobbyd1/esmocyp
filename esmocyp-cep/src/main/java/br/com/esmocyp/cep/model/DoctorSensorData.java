package br.com.esmocyp.cep.model;

/**
 * Created by ruhan on 23/04/17.
 */
public class DoctorSensorData {

    private String idSmartphone;
    private double latitude;
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
