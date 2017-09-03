package br.com.esmocyp.cep.listeners;

import com.espertech.esper.client.UpdateListener;

/**
 * Created by ruhan on 02/09/17.
 */
public interface FullRoomEventListenerDLO extends UpdateListener {
    void processRoomEventListener( String roomId, Long entering, Long leaving );
}
