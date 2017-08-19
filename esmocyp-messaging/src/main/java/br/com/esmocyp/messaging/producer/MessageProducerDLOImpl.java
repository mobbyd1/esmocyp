package br.com.esmocyp.messaging.producer;

import com.google.gson.Gson;
import br.com.esmocyp.messaging.model.IMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import br.com.esmocyp.messaging.topic.EsmocypTopic;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@Component
public class MessageProducerDLOImpl implements MessageProducerDLO {

    private Producer<String, String> producer;
    private final Properties properties = new Properties();

    @PostConstruct
    public void init() {

        //Assign localhost id
        properties.put("bootstrap.servers", "localhost:9092");

        //Set acknowledgements for br.com.esmocyp.messaging.producer requests.
        properties.put("acks", "all");

        //If the request fails, the br.com.esmocyp.messaging.producer can automatically retry,
        properties.put("retries", 0);

        //Specify buffer size in config
        properties.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        properties.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the br.com.esmocyp.messaging.producer for buffering.
        properties.put("buffer.memory", 33554432);

        properties.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(properties);
    }

    public void produceMessage(EsmocypTopic topic, IMessage message) {
        String topicName = topic.toString();

        final Gson gson = new Gson();
        final String jsonMessage = gson.toJson(message);

        ProducerRecord<String, String> producerMsg = new ProducerRecord<String, String>( topicName, jsonMessage );
        producer.send( producerMsg );
    }
}
