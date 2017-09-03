package br.com.esmocyp.cep.test.rules;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLO;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import br.com.esmocyp.cep.service.CEPController;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import com.espertech.esper.client.EPRuntime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by ruhandosreis on 01/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCEPRuleFullRoom2 extends AbstractTestCEPRule {

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
    public void testCEPRuleFullRoom2() throws InterruptedException {
        cepController.destroy();
        cepController.init();

        final EPRuntime epRuntime = cepController.getEpRuntime();

        for( int i = 0; i < 10; i++ ) {
            final LeavingRoomSensorData leavingRoomSensorData = new LeavingRoomSensorData();
            leavingRoomSensorData.setRoomId( ROOM_ID );

            epRuntime.sendEvent( leavingRoomSensorData );

            Thread.sleep( 1000 );
        }

        Thread.sleep(10000);

        verify( fullRoomEventListener, times(0) ).update( any(), any() );

    }
}
