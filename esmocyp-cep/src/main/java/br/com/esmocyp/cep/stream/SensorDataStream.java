package br.com.esmocyp.cep.stream;

import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import com.espertech.esper.client.EPRuntime;

/**
 * Created by ruhan on 23/04/17.
 */
public class SensorDataStream implements Runnable {

    private static final String ROOM_ID = "saladeEspera1";
    private EPRuntime epRuntime;

    public SensorDataStream( final EPRuntime epRuntime ) {
        this.epRuntime = epRuntime;
    }

    public void run() {

        int i = 0;
        while( true ) {
//           if( i < 5 ) {
                final EnteringRoomSensorData enteringRoomSensorData = new EnteringRoomSensorData();
                enteringRoomSensorData.setRoomId( ROOM_ID );

                epRuntime.sendEvent( enteringRoomSensorData );

//           } else if( i < 10 ) {
//                final LeavingRoomSensorData leavingRoomSensorData = new LeavingRoomSensorData();
//                leavingRoomSensorData.setRoomId( ROOM_ID );
//
//                epRuntime.sendEvent( leavingRoomSensorData );
//           }

           try {

               Thread.sleep( 1000 );

           } catch (InterruptedException e) {
               e.printStackTrace();
           }

            i++;
        }

//        for (int i = 0; i < 10; i++) {
//
//            if (i < 5) {
//                final LeavingRoomSensorData leavingRoomSensorData = new LeavingRoomSensorData();
//                leavingRoomSensorData.setRoomId(ROOM_ID);
//
//                epRuntime.sendEvent(leavingRoomSensorData);
//
//            } else {
//                final EnteringRoomSensorData enteringRoomSensorData = new EnteringRoomSensorData();
//                enteringRoomSensorData.setRoomId(ROOM_ID);
//
//                epRuntime.sendEvent(enteringRoomSensorData);
//            }
//
//            try {
//
//                Thread.sleep(1000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//
//            Thread.sleep(10000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
