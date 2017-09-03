package br.com.esmocyp.messaging.model;

/**
 * Created by ruhandosreis on 16/08/17.
 *
 * Define a interface to use create a new message to send and receive from the broker.
 * All messages must implement this interface.
 */
public interface IMessage {

    /**
     * Gets the concret class name
     * @return
     */
    String getClassName();
}
