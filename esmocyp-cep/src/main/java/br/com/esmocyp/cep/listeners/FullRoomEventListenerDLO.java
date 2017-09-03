package br.com.esmocyp.cep.listeners;

import com.espertech.esper.client.UpdateListener;

/**
 * Created by ruhan on 02/09/17.
 *
 * Defines a interface to notify of new and old events of the types
 * EnteringRoomSensorData and LeavingRoomSensorData
 */
public interface FullRoomEventListenerDLO extends UpdateListener {

    /**
     * Generates new RDF triples based on the parameters
     *
     * @param roomId
     * @param entering number of people entering the room
     * @param leaving number of people leaving the room
     */
    void processRoomEventListener( String roomId, Long entering, Long leaving );
}
