package br.com.esmocyp.cep.stream;

import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import com.espertech.esper.client.EPRuntime;

/**
 * Created by ruhan on 23/04/17.
 */
public class SensorDataStream implements Runnable {

    private static final String ROOM_ID = "salaDoRuhan";
    private static final String SMARTPHONE_ID = "smartphoneDoRuhan";
    private EPRuntime epRuntime;

    public SensorDataStream( final EPRuntime epRuntime ) {
        this.epRuntime = epRuntime;
    }

    public void run() {

        int i = 1;
        while( true ) {

            if( i > 1 ) {
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
                Thread.sleep( 1 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i++;
        }
    }
}
