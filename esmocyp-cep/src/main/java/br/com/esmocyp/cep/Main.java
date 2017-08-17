package br.com.esmocyp.cep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"br.com.esmocyp.messaging", "br.com.esmocyp.cep"})
public class Main {

    public static void main(String args[]) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        //applicationContext.getBean( CEPController.class );

    }
}
