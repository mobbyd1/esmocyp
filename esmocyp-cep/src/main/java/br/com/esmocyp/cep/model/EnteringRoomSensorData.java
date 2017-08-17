package br.com.esmocyp.messaging.model;

/**
 * Created by ruhandosreis on 29/04/17.
 */
public class EnteringRoomSensorData {

    private String roomId;
    private String idSmartphone;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getIdSmartphone() {
        return idSmartphone;
    }

    public void setIdSmartphone(String idSmartphone) {
        this.idSmartphone = idSmartphone;
    }
}
