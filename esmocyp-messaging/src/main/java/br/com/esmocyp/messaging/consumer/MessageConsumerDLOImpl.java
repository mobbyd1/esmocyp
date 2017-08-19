package br.com.esmocyp.messaging.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import br.com.esmocyp.messaging.topic.EsmocypTopic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public class MessageConsumerDLOImpl implements MessageConsumerDLO {

    public void registerConsumer(EsmocypTopic topic, MessageConsumerCallback callback) {
        final String topicName = topic.toString();

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer
                <String, String>(props);

        final ConsumerThread consumerThread = new ConsumerThread(consumer, topicName, callback);

        final Thread thread = new Thread(consumerThread);
        thread.start();
    }
}
