package br.com.esmocyp.cep.listeners;

import com.espertech.esper.client.UpdateListener;

/**
 * Created by ruhandosreis on 02/09/17.
 *
 * Defines a interface to notify of new events and old events of the type DoctorSensorData
 */
public interface DoctorEventListenerDLO extends UpdateListener{

    /**
     * Generates a RDF triple based on the parameters
     *
     * @param smartphoneId
     * @param in
     * @param out
     */
    void processDoctorEventListener( String smartphoneId, Long in, Long out );
}
