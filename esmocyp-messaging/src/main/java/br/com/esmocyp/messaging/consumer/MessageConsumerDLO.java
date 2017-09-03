package br.com.esmocyp.messaging.consumer;

import br.com.esmocyp.messaging.topic.EsmocypTopic;

/**
 * Created by ruhandosreis on 16/08/17.
 *
 * Defines a interface to consume messages from the broker
 */
public interface MessageConsumerDLO {

    /**
     * Registers a new consumer to the broker
     * @param topic the topic that the consumer will monitor
     * @param callback the callback that will be executed when it has new messages
     */
    void registerConsumer(EsmocypTopic topic, MessageConsumerCallback callback);
}
