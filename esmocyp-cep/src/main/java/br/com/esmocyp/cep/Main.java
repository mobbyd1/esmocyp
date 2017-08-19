package br.com.esmocyp.cep;

import br.com.esmocyp.cep.service.CEPController;
import br.com.esmocyp.cep.stream.DoctorSensorDataStream;
import br.com.esmocyp.cep.stream.SensorDataStream;
import com.espertech.esper.client.EPRuntime;
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

        final CEPController cepController = applicationContext.getBean(CEPController.class);
        final EPRuntime epRuntime = cepController.getEpRuntime();

        final DoctorSensorDataStream doctorSensorDataStream = new DoctorSensorDataStream(epRuntime);
        doctorSensorDataStream.start();

        final SensorDataStream sensorDataStream = new SensorDataStream(epRuntime);
        sensorDataStream.start();
    }
}
