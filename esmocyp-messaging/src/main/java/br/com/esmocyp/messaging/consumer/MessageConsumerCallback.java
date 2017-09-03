package br.com.esmocyp.messaging.consumer;

import br.com.esmocyp.messaging.model.IMessage;

/**
 * Created by ruhandosreis on 16/08/17.
 *
 * Defines a interface to create callbacks for processing messages
 */
public interface MessageConsumerCallback {

    /**
     * Executes the callback consumer
     *
     * @param message message to be processed
     */
    void executeConsumer( IMessage message );
}
