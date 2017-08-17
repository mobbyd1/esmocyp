package br.com.esmocyp.cep.message;

import br.com.esmocyp.messaging.model.RdfMessage;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public interface RDFMessageProducerDLO {
    void produceRDFMessage(RdfMessage message);
}
