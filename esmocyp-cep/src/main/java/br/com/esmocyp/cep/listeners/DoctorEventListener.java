package br.com.esmocyp.cep.listeners;

import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@Component
public class DoctorEventListener implements UpdateListener {

    private static String BASE_URL = "urn:x-hp:eg/";

    @Autowired
    private MessageProducerDLO messageProducerDLO;

    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {

        for( int i = 0; i < eventBeans.length; i++ ) {
            final EventBean eventBean = eventBeans[i];
            final String idSmartphone = ( String ) eventBean.get("idSmartphone");

            final RdfMessage rdfMessage = new RdfMessage();
            rdfMessage.setSubject( BASE_URL + idSmartphone );
            rdfMessage.setPredicate( "rdf:type" );
            rdfMessage.setObject( BASE_URL + "NaoEncontrado" );

            messageProducerDLO.produceMessage( EsmocypTopic.CEP_RESULT_TOPIC, rdfMessage );
        }
    }
}
