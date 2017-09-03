package br.com.esmocyp.cep.test.listeners;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.DoctorEventListenerDLOImpl;
import br.com.esmocyp.messaging.model.IMessage;
import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by ruhan on 02/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestListenerDoctor {

    @Mock
    MessageProducerDLO messageProducerDLO;

    @InjectMocks
    DoctorEventListenerDLOImpl doctorEventListenerDLO;

    private static final String BASE_URL = "urn:x-hp:eg/";
    private static final String UUID = "SMARTPHONE_TEST";
    private static final String NAO_ENCONTRADO = "NaoEncontrado";

    @Before
    public void initTests() {
        Mockito.doNothing().when( messageProducerDLO ).init();
    }

    @Test
    public void testListenerDoctor1() {

        doctorEventListenerDLO.processDoctorEventListener(
                    UUID
                ,   Long.valueOf(0)
                ,   Long.valueOf(10) );

        final ArgumentCaptor<EsmocypTopic> esmocypTopicArgumentCaptor = ArgumentCaptor.forClass(EsmocypTopic.class);
        final ArgumentCaptor<IMessage> messageArgumentCaptor = ArgumentCaptor.forClass(IMessage.class);

        verify( messageProducerDLO, times(1) ).produceMessage(
                esmocypTopicArgumentCaptor.capture()
                ,   messageArgumentCaptor.capture() );

        final EsmocypTopic actualTopic = esmocypTopicArgumentCaptor.getValue();
        final RdfMessage actualMessage = (RdfMessage) messageArgumentCaptor.getValue();

        Assert.assertEquals( EsmocypTopic.CEP_RESULT_TOPIC, actualTopic );

        final String actualMessageSubject = actualMessage.getSubject();
        final String actualMessagePredicate = actualMessage.getPredicate();
        final String actualMessageObject = actualMessage.getObject();

        Assert.assertEquals( BASE_URL + UUID, actualMessageSubject );
        Assert.assertEquals( "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", actualMessagePredicate );
        Assert.assertEquals( BASE_URL + NAO_ENCONTRADO, actualMessageObject );
    }

    @Test
    public void testListenerDoctor2() {

        doctorEventListenerDLO.processDoctorEventListener(
                UUID
                ,   Long.valueOf(10)
                ,   Long.valueOf(0) );

        final ArgumentCaptor<EsmocypTopic> esmocypTopicArgumentCaptor = ArgumentCaptor.forClass(EsmocypTopic.class);
        final ArgumentCaptor<IMessage> messageArgumentCaptor = ArgumentCaptor.forClass(IMessage.class);

        verify( messageProducerDLO, times(0) ).produceMessage( any(), any() );
    }

    @Test
    public void testListenerDoctor3() {

        doctorEventListenerDLO.processDoctorEventListener(
                UUID
                ,   Long.valueOf(10)
                ,   Long.valueOf(10) );

        final ArgumentCaptor<EsmocypTopic> esmocypTopicArgumentCaptor = ArgumentCaptor.forClass(EsmocypTopic.class);
        final ArgumentCaptor<IMessage> messageArgumentCaptor = ArgumentCaptor.forClass(IMessage.class);

        verify( messageProducerDLO, times(0) ).produceMessage( any(), any() );
    }

}
