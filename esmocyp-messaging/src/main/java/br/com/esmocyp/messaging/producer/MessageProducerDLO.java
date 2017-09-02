package br.com.esmocyp.messaging.producer;

import br.com.esmocyp.messaging.model.IMessage;
import br.com.esmocyp.messaging.topic.EsmocypTopic;

/**
 * Created by ruhandosreis on 15/08/17.
 */
public interface MessageProducerDLO {
    void init();
    void produceMessage(EsmocypTopic topic, IMessage message);
}
