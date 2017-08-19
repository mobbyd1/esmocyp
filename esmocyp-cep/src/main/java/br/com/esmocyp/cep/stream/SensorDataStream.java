package br.com.esmocyp.cep.stream;

import br.com.esmocyp.messaging.model.EnteringRoomSensorData;
import br.com.esmocyp.messaging.model.LeavingRoomSensorData;
import com.espertech.esper.client.EPRuntime;

/**
 * Created by ruhan on 23/04/17.
 */
public class SensorDataStream extends Thread {

    private static final String ROOM_ID = "sala1";
    private static final String SMARTPHONE_ID = "smartphoneDoRuhan";
    private EPRuntime epRuntime;

    public SensorDataStream( final EPRuntime epRuntime ) {
        this.epRuntime = epRuntime;
    }

    public void run() {

        int i = 1;
        while( true ) {

            if( i > 3 ) {
                final EnteringRoomSensorData enteringRoomSensorData = new EnteringRoomSensorData();
                enteringRoomSensorData.setRoomId( ROOM_ID );
                enteringRoomSensorData.setIdSmartphone( SMARTPHONE_ID );

                epRuntime.sendEvent( enteringRoomSensorData );

            } else {
                final LeavingRoomSensorData leavingRoomSensorData = new LeavingRoomSensorData();
                leavingRoomSensorData.setRoomId( ROOM_ID );
                leavingRoomSensorData.setIdSmartphone( SMARTPHONE_ID );

                epRuntime.sendEvent( leavingRoomSensorData );
            }

            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i++;
        }
    }
}
