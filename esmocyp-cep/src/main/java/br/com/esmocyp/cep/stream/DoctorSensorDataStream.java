package br.com.esmocyp.cep.stream;

import br.com.esmocyp.messaging.model.DoctorSensorData;
import com.espertech.esper.client.EPRuntime;
/**
 * Created by ruhan on 28/05/17.
 */
public class DoctorSensorDataStream extends Thread {

    private static final String SMARTPHONE_ID = "smartphoneDoRuhan";
    private EPRuntime epRuntime;

    public DoctorSensorDataStream( final EPRuntime epRuntime ) {
        this.epRuntime = epRuntime;
    }

    @Override
    public void run() {

        for(;;) {

            final DoctorSensorData doctorSensorData = new DoctorSensorData();

            doctorSensorData.setIdSmartphone( SMARTPHONE_ID );
            doctorSensorData.setLatitude( 1d );
            doctorSensorData.setLongitude( 1d );

            epRuntime.sendEvent( doctorSensorData );

            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
