package br.com.esmocyp.cep.stream;

import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import com.espertech.esper.client.EPRuntime;

/**
 * Created by ruhan on 23/04/17.
 *
 * Test data for in/out the general waiting room
 */
public class SensorDataStream implements Runnable {

    private static final String ROOM_ID = "saladeEspera1";
    private EPRuntime epRuntime;

    public SensorDataStream( final EPRuntime epRuntime ) {
        this.epRuntime = epRuntime;
    }

    public void run() {

        while( true ) {

            final EnteringRoomSensorData enteringRoomSensorData = new EnteringRoomSensorData();
            enteringRoomSensorData.setRoomId( ROOM_ID );

            epRuntime.sendEvent( enteringRoomSensorData );

            try {

               Thread.sleep( 1000 );

            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }
}
