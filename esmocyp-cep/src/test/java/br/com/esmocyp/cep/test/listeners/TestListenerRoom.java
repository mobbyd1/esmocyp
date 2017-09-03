package br.com.esmocyp.cep.test.listeners;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLOImpl;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLOImpl;
import br.com.esmocyp.messaging.model.IMessage;
import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by ruhan on 02/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestListenerRoom {

    @Mock
    MessageProducerDLO messageProducerDLO;

    @InjectMocks
    FullRoomEventListenerDLOImpl fullRoomEventListenerDLO;

    private static final String BASE_URL = "urn:x-hp:eg/";
    private static final String ROOM_ID = "ROOM_ID_TEST";
    private static final String SALA_CHEIA = "SalaCheia";

    @Before
    public void initTests() {
        Mockito.doNothing().when( messageProducerDLO ).init();
    }

    @Test
    public void testListenerRoom1() {

        fullRoomEventListenerDLO.processRoomEventListener(
                ROOM_ID
                ,   Long.valueOf(10)
                ,   Long.valueOf(0) );

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

        Assert.assertEquals( BASE_URL + ROOM_ID, actualMessageSubject );
        Assert.assertEquals( "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", actualMessagePredicate );
        Assert.assertEquals( BASE_URL + SALA_CHEIA, actualMessageObject );
    }

    @Test
    public void testListenerRoom2() {

        fullRoomEventListenerDLO.processRoomEventListener(
                ROOM_ID
                ,   Long.valueOf(0)
                ,   Long.valueOf(10) );

        final ArgumentCaptor<EsmocypTopic> esmocypTopicArgumentCaptor = ArgumentCaptor.forClass(EsmocypTopic.class);
        final ArgumentCaptor<IMessage> messageArgumentCaptor = ArgumentCaptor.forClass(IMessage.class);

        verify( messageProducerDLO, times(0) ).produceMessage( any(), any() );
    }

    @Test
    public void testListenerRoom3() {

        fullRoomEventListenerDLO.processRoomEventListener(
                ROOM_ID
                ,   Long.valueOf(10)
                ,   Long.valueOf(10) );

        final ArgumentCaptor<EsmocypTopic> esmocypTopicArgumentCaptor = ArgumentCaptor.forClass(EsmocypTopic.class);
        final ArgumentCaptor<IMessage> messageArgumentCaptor = ArgumentCaptor.forClass(IMessage.class);

        verify( messageProducerDLO, times(0) ).produceMessage( any(), any() );
    }

}
