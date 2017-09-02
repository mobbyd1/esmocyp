package br.com.esmocyp.cep.stream;

import br.com.esmocyp.cep.model.DoctorSensorData;
import com.espertech.esper.client.EPRuntime;
/**
 * Created by ruhan on 28/05/17.
 */
public class DoctorSensorDataStream implements Runnable {

    private static final String SMARTPHONE_ID = "smartphoneDoRuhan";
    private EPRuntime epRuntime;

    public DoctorSensorDataStream( final EPRuntime epRuntime ) {
        this.epRuntime = epRuntime;
    }

    @Override
    public void run() {

        for( int i = 0; i < 10; i++ ) {

            if( i < 8 ) {
                final DoctorSensorData doctorSensorData = new DoctorSensorData();

                doctorSensorData.setIdSmartphone(SMARTPHONE_ID);
                doctorSensorData.setLatitude(1d);
                    doctorSensorData.setLongitude(1d);

                epRuntime.sendEvent( doctorSensorData );

            } else {
                final DoctorSensorData doctorSensorData2 = new DoctorSensorData();

                doctorSensorData2.setIdSmartphone(SMARTPHONE_ID);
                doctorSensorData2.setLatitude(-22.866891);
                doctorSensorData2.setLongitude(-43.255070);

                epRuntime.sendEvent(doctorSensorData2);
            }


            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
