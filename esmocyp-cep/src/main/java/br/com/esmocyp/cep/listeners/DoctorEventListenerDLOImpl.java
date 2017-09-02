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
public class DoctorEventListenerDLOImpl implements DoctorEventListenerDLO {

    private static String BASE_URL = "urn:x-hp:eg/";

    private MessageProducerDLO messageProducerDLO;

    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {

        for( int i = 0; i < eventBeans.length; i++ ) {
            final EventBean eventBean = eventBeans[i];

            final String idSmartphone = ( String ) eventBean.get("idSmartphone");
            final Long notInHospitalCount = ( Long ) eventBean.get("notInHospitalCount");
            final Long inHospitalCount = ( Long ) eventBean.get("inHospitalCount");

            processDoctorEventListener( idSmartphone, inHospitalCount, notInHospitalCount );

        }
    }

    @Override
    public void processDoctorEventListener(String smartphoneId, Long in, Long out) {

        System.out.println( new Date() + " In: " + in + " | Out: " + out );

        if( ( out != null && out != 0 && in == null )
                || out > in ) {

            final RdfMessage rdfMessage = new RdfMessage();
            rdfMessage.setSubject(BASE_URL + smartphoneId);
            rdfMessage.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
            rdfMessage.setObject(BASE_URL + "NaoEncontrado");

           // messageProducerDLO.produceMessage(EsmocypTopic.CEP_RESULT_TOPIC, rdfMessage);
        }
    }
}
