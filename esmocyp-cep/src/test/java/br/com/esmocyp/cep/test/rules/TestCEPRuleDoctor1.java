package br.com.esmocyp.cep.test.rules;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLO;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by ruhandosreis on 01/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCEPRuleDoctor1 extends AbstractTestCEPRule {

    @Mock
    MessageProducerDLO messageProducerDLO;

    @Mock
    DoctorEventListenerDLO doctorEventListener;

    @Mock
    FullRoomEventListenerDLO fullRoomEventListener;

    @InjectMocks
    CEPController cepController;

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

        Thread.sleep(10000);

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
}
