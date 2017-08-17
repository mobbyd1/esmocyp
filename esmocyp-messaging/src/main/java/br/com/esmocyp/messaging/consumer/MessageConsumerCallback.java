package br.com.esmocyp.messaging.consumer;

import br.com.esmocyp.messaging.model.IMessage;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public interface MessageConsumerCallback {
    void executeConsumer( IMessage message );
}
