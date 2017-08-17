package br.com.esmocyp.messaging.consumer;

import br.com.esmocyp.messaging.topic.EsmocypTopic;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public interface MessageConsumerDLO {
    void registerConsumer(EsmocypTopic topic, MessageConsumerCallback callback);
}
