package br.com.esmocyp.cep.test.rules;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLO;
import br.com.esmocyp.cep.model.EnteringRoomSensorData;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by ruhandosreis on 01/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCEPRuleFullRoom1 extends AbstractTestCEPRule {

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
    public void testCEPRuleFullRoom1() throws InterruptedException {
        cepController.destroy();
        cepController.init();

        final EPRuntime epRuntime = cepController.getEpRuntime();

        for( int i = 0; i < 10; i++ ) {
            final EnteringRoomSensorData enteringRoomSensorData = new EnteringRoomSensorData();
            enteringRoomSensorData.setRoomId( ROOM_ID );

            epRuntime.sendEvent( enteringRoomSensorData );

            Thread.sleep( 1000 );
        }

        Thread.sleep(10000);

        final ArgumentCaptor<EventBean[]> objectArgumentCaptor1 = ArgumentCaptor.forClass(EventBean[].class);
        final ArgumentCaptor<EventBean[]> objectArgumentCaptor2 = ArgumentCaptor.forClass(EventBean[].class);

        verify( fullRoomEventListener ).update( objectArgumentCaptor1.capture(), objectArgumentCaptor2.capture() );

        final EventBean[] eventBeans = objectArgumentCaptor1.getValue();
        final EventBean eventBean = eventBeans[0];

        final Long leavingCountActual = ( Long ) eventBean.get("leavingCount");
        final Long enteringCountActual =  ( Long ) eventBean.get("enteringCount");

        final String roomIdActual = ( String ) eventBean.get("enteringRoomId");

        Assert.assertNull( leavingCountActual );
        Assert.assertEquals( Long.valueOf(10), enteringCountActual );
        Assert.assertEquals( ROOM_ID, roomIdActual );
    }
}
