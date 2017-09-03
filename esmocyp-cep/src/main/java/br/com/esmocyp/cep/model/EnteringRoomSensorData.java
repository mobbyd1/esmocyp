package br.com.esmocyp.cep.model;

/**
 * Created by ruhandosreis on 29/04/17.
 *
 * This class represents a stream of entering a room
 */
public class EnteringRoomSensorData {

    /**
     * Room identification
     */
    private String roomId;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}
