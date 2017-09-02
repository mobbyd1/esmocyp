package br.com.esmocyp.reasoning.service;

import br.com.esmocyp.messaging.consumer.MessageConsumerCallback;
import br.com.esmocyp.messaging.consumer.MessageConsumerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;
import br.com.esmocyp.reasoning.stream.RDFStreamListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ruhandosreis on 18/08/17.
 */
@Component
public class ServiceDLO {

    @Autowired
    private MessageConsumerCallback rdfStreamListener;

    @Autowired
    private MessageConsumerDLO messageConsumerDLO;

    @PostConstruct
    public void init() {
        messageConsumerDLO.registerConsumer( EsmocypTopic.CEP_RESULT_TOPIC, rdfStreamListener );
    }
}
