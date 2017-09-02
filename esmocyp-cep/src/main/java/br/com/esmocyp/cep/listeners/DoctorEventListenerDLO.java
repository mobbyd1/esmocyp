package br.com.esmocyp.cep.listeners;

import com.espertech.esper.client.UpdateListener;

/**
 * Created by ruhandosreis on 02/09/17.
 */
public interface DoctorEventListenerDLO extends UpdateListener{
    void processDoctorEventListener( String smartphoneId, Long in, Long out );
}
