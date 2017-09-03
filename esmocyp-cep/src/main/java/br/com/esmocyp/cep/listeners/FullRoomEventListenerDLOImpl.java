package br.com.esmocyp.cep.listeners;

import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@Component
public class FullRoomEventListenerDLOImpl implements FullRoomEventListenerDLO {

    private static String BASE_URL = "urn:x-hp:eg/";

    @Autowired
    private MessageProducerDLO messageProducerDLO;

    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {

        for ( int i = 0; i < eventBeans.length; i++ ) {
            final EventBean eventBean = eventBeans[i];

            final Long leavingCount = ( Long ) eventBean.get("leavingCount");
            final Long enteringCount =  ( Long ) eventBean.get("enteringCount");

            final String roomId = ( String ) eventBean.get("enteringRoomId");

            processRoomEventListener( roomId, enteringCount, leavingCount );
        }
    }

    @Override
    public void processRoomEventListener(String roomId, Long entering, Long leaving) {

        System.out.println( new Date() + " Entering: " + entering + " | Leaving: " + leaving );

        if( ( leaving == null && entering != null ) || entering > leaving ) {
            final RdfMessage rdfMessage = new RdfMessage();
            rdfMessage.setSubject(BASE_URL + roomId);
            rdfMessage.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
            rdfMessage.setObject(BASE_URL + "SalaCheia");

           messageProducerDLO.produceMessage( EsmocypTopic.CEP_RESULT_TOPIC, rdfMessage );
        }
    }
}
