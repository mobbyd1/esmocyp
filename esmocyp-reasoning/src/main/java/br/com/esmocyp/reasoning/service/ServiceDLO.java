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
 *
 * This class is responsible for initiate the reasoning service.
 * The rdfStreamListener is registered as a callback for new RDF messages
 *
 */
@Component
public class ServiceDLO {

    @Autowired
    private MessageConsumerCallback rdfStreamListener;

    @Autowired
    private MessageConsumerDLO messageConsumerDLO;

    @Autowired
    StreamReasoningDLO streamReasoningDLO;

    /**
     * Initializes the messageConsumer and the continous query engine
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        messageConsumerDLO.registerConsumer( EsmocypTopic.CEP_RESULT_TOPIC, rdfStreamListener );
        streamReasoningDLO.init( "a-box/esmocypData.rdf", "t-box/tbox-esmocyp.rdf" );
    }
}
