package br.com.esmocyp.cep.message;

import br.com.esmocyp.messaging.model.RdfMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.esmocyp.messaging.producer.MessageProducerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@Component
public class RDFMessageProducerDLOImpl implements RDFMessageProducerDLO {

    @Autowired
    private MessageProducerDLO messageProducerDLO;

    @Override
    public void produceRDFMessage(RdfMessage message) {
        messageProducerDLO.produceMessage( EsmocypTopic.CEP_RESULT_TOPIC, message );
    }
}
