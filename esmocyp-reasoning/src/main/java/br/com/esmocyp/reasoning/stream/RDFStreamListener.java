package br.com.esmocyp.reasoning.stream;

import br.com.esmocyp.messaging.consumer.MessageConsumerCallback;
import br.com.esmocyp.messaging.model.IMessage;
import br.com.esmocyp.messaging.model.RdfMessage;
import br.com.esmocyp.reasoning.service.StreamReasoningDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ruhandosreis on 18/08/17.
 *
 * Listener responsible to insert new RDF messages to the continous query engine
 */
@Component
public class RDFStreamListener implements MessageConsumerCallback {

    @Autowired
    private StreamReasoningDLO streamReasoningDLO;

    @Override
    public void executeConsumer(IMessage message) {
        streamReasoningDLO.insertTriple( ( RdfMessage ) message );
    }
}
