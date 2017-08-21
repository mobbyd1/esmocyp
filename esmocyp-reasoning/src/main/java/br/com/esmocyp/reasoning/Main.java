package br.com.esmocyp.reasoning;

import br.com.esmocyp.messaging.consumer.MessageConsumerDLO;
import br.com.esmocyp.messaging.topic.EsmocypTopic;
import br.com.esmocyp.reasoning.stream.RDFStreamListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ruhandosreis on 19/08/17.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"br.com.esmocyp.messaging", "br.com.esmocyp.reasoning"})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
