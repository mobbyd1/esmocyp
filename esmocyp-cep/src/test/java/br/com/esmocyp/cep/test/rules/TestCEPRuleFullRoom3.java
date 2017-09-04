package br.com.esmocyp.cep.test.rules;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLO;
import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import br.com.esmocyp.cep.service.CEPController;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by ruhandosreis on 01/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCEPRuleFullRoom3 extends AbstractTestCEPRule {

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
    public void testCEPRuleFullRoom3() throws InterruptedException {
        cepController.destroy();
        cepController.init();

        final EPRuntime epRuntime = cepController.getEpRuntime();

        for( int i = 0; i < 10; i++ ) {

            if (i < 5) {
                final LeavingRoomSensorData leavingRoomSensorData = new LeavingRoomSensorData();
                leavingRoomSensorData.setRoomId(ROOM_ID);

                epRuntime.sendEvent( leavingRoomSensorData );

            } else {
                final EnteringRoomSensorData enteringRoomSensorData = new EnteringRoomSensorData();
                enteringRoomSensorData.setRoomId(ROOM_ID);

                epRuntime.sendEvent( enteringRoomSensorData );
            }

            Thread.sleep( 1000 );
        }

        Thread.sleep( 10000 );

        final ArgumentCaptor<EventBean[]> objectArgumentCaptor1 = ArgumentCaptor.forClass(EventBean[].class);
        final ArgumentCaptor<EventBean[]> objectArgumentCaptor2 = ArgumentCaptor.forClass(EventBean[].class);

        verify( fullRoomEventListener, atLeast(1) ).update( objectArgumentCaptor1.capture(), objectArgumentCaptor2.capture() );

        final List<EventBean[]> listEventBeans = objectArgumentCaptor1.getAllValues();
        final EventBean eventBean = listEventBeans.get(0)[0];

        final Long leavingCountActual = ( Long ) eventBean.get("leavingCount");
        final Long enteringCountActual =  ( Long ) eventBean.get("enteringCount");

        final String roomIdActual = ( String ) eventBean.get("enteringRoomId");

        Assert.assertEquals( Long.valueOf(5), leavingCountActual );
        Assert.assertEquals( Long.valueOf(5), enteringCountActual );
        Assert.assertEquals( ROOM_ID, roomIdActual );
    }
}
