package br.com.esmocyp.messaging.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.com.esmocyp.messaging.json.ConvertableDeserializer;
import br.com.esmocyp.messaging.model.IMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public class ConsumerThread implements Runnable {

    private KafkaConsumer<String, String> consumer;
    private MessageConsumerCallback callback;

    public ConsumerThread(
                final KafkaConsumer<String, String> consumer
            ,   final String topicName
            ,   final MessageConsumerCallback callback ) {

        this.consumer = consumer;
        this.callback = callback;
        this.consumer.subscribe( Arrays.asList( topicName ) );
    }

    public void run() {
        final GsonBuilder builder = new GsonBuilder();

        // Adding custom deserializers
        builder.registerTypeAdapter(IMessage.class, new ConvertableDeserializer<IMessage>());
        final Gson gson = builder.create();

        while( true ) {
            final ConsumerRecords<String, String> records = consumer.poll( 1000 );

            for( final ConsumerRecord<String, String> record : records ) {
                final String value = record.value();

                final IMessage message = gson.fromJson(value, IMessage.class);
                callback.executeConsumer( message );
            }
        }
    }
}
