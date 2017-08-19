package br.com.esmocyp.reasoning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
