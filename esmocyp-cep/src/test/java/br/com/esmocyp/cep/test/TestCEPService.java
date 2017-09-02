package br.com.esmocyp.cep.test;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.DoctorEventListenerDLOImpl;
import br.com.esmocyp.cep.listeners.FullRoomEventListener;
import br.com.esmocyp.cep.model.DoctorSensorData;
import br.com.esmocyp.cep.service.CEPController;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.lang.reflect.Array;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ruhandosreis on 01/09/17.
 */
@RunWith(MockitoJUnitRunner.class)

public class TestCEPService {

    @Mock
    MessageProducerDLO messageProducerDLO;

    @Mock
    DoctorEventListenerDLO doctorEventListener;

    @Mock
    FullRoomEventListener fullRoomEventListener;

    @InjectMocks
    CEPController cepController;

    private static final String UUID = "SMARTPHONE_TEST";

    @Before
    public void initTests() {
        Mockito.doNothing().when( messageProducerDLO ).init();
    }

    @Test
    public void testCEPRuleDoctor1() throws Exception {

        cepController.init();

        final EPRuntime epRuntime = cepController.getEpRuntime();

        for( int i = 0; i < 10; i++ ) {

            final DoctorSensorData doctorSensorData = new DoctorSensorData();

            doctorSensorData.setIdSmartphone( UUID );
            doctorSensorData.setLatitude( 1d );
            doctorSensorData.setLongitude( 1d );

            epRuntime.sendEvent( doctorSensorData );

            Thread.sleep( 1000 );
        }

        final ArgumentCaptor<EventBean[]> objectArgumentCaptor1 = ArgumentCaptor.forClass(EventBean[].class);
        final ArgumentCaptor<EventBean[]> objectArgumentCaptor2 = ArgumentCaptor.forClass(EventBean[].class);

        verify( doctorEventListener ).update( objectArgumentCaptor1.capture(), objectArgumentCaptor2.capture() );

        final EventBean[] eventBeans = objectArgumentCaptor1.getValue();
        final EventBean eventBean = eventBeans[0];

        final String idSmartphoneActual = ( String ) eventBean.get("idSmartphone");
        final Long notInHospitalCountActual = ( Long ) eventBean.get("notInHospitalCount");
        final Long inHospitalCountActual = ( Long ) eventBean.get("inHospitalCount");

        Assert.assertEquals( UUID, idSmartphoneActual );
        Assert.assertEquals( Long.valueOf( 10 ), notInHospitalCountActual );
        Assert.assertNull( inHospitalCountActual );
    }

    @Test
    public void testCEPRuleDoctor2() throws Exception {

        cepController.init();

        final EPRuntime epRuntime = cepController.getEpRuntime();

        for( int i = 0; i < 10; i++ ) {

            final DoctorSensorData doctorSensorData = new DoctorSensorData();

            doctorSensorData.setIdSmartphone( UUID );
            doctorSensorData.setLatitude( -22.866891 );
            doctorSensorData.setLongitude( -43.255070 );

            epRuntime.sendEvent( doctorSensorData );

            Thread.sleep( 1000 );
        }

        verify( doctorEventListener, times(0) ).update( any(), any() );
    }

    @Test
    public void testCEPRuleDoctor3() throws Exception {

        cepController.init();

        final EPRuntime epRuntime = cepController.getEpRuntime();

        for( int i = 0; i < 10; i++ ) {

            if( i < 5 ) {
                final DoctorSensorData doctorSensorData = new DoctorSensorData();

                doctorSensorData.setIdSmartphone(UUID);
                doctorSensorData.setLatitude(-22.866891);
                doctorSensorData.setLongitude(-43.255070);

                epRuntime.sendEvent(doctorSensorData);

            } else {
                final DoctorSensorData doctorSensorData = new DoctorSensorData();

                doctorSensorData.setIdSmartphone(UUID);
                doctorSensorData.setLatitude( 1d );
                doctorSensorData.setLongitude( 1d );

                epRuntime.sendEvent(doctorSensorData);
            }

            Thread.sleep( 1000 );
        }

        final ArgumentCaptor<EventBean[]> objectArgumentCaptor1 = ArgumentCaptor.forClass(EventBean[].class);
        final ArgumentCaptor<EventBean[]> objectArgumentCaptor2 = ArgumentCaptor.forClass(EventBean[].class);

        verify( doctorEventListener ).update( objectArgumentCaptor1.capture(), objectArgumentCaptor2.capture() );

        final EventBean[] eventBeans = objectArgumentCaptor1.getValue();
        final EventBean eventBean = eventBeans[0];

        final String idSmartphoneActual = ( String ) eventBean.get("idSmartphone");
        final Long notInHospitalCountActual = ( Long ) eventBean.get("notInHospitalCount");
        final Long inHospitalCountActual = ( Long ) eventBean.get("inHospitalCount");

        Assert.assertEquals( UUID, idSmartphoneActual );
        Assert.assertEquals( Long.valueOf( 5 ), notInHospitalCountActual );
        Assert.assertEquals( Long.valueOf( 5 ), inHospitalCountActual );

    }
}
