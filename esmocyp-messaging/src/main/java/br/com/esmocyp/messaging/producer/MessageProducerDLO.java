package br.com.esmocyp.messaging.producer;

import br.com.esmocyp.messaging.model.IMessage;
import br.com.esmocyp.messaging.topic.EsmocypTopic;

/**
 * Created by ruhandosreis on 15/08/17.
 *
 * Defines a interface to initialize and new messages to the broker
 */
public interface MessageProducerDLO {

    /**
     * Initializes the broker connection to produce new messages
     */
    void init();

    /**
     * Send a message to the specific topic
     *
     * @param topic the topic that the message will be sent
     * @param message the message that will be sent
     */
    void produceMessage(EsmocypTopic topic, IMessage message);
}
