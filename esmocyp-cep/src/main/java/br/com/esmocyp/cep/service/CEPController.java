package br.com.esmocyp.cep.service;

import com.espertech.esper.client.*;
import br.com.esmocyp.cep.listeners.DoctorEventListener;
import br.com.esmocyp.cep.listeners.FullRoomEventListener;
import br.com.esmocyp.messaging.model.DoctorSensorData;
import br.com.esmocyp.messaging.model.EnteringRoomSensorData;
import br.com.esmocyp.messaging.model.LeavingRoomSensorData;
import br.com.esmocyp.cep.util.GPSIsInArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ruhandosreis on 16/08/17.
 */
@Component
public class CEPController {

    @Autowired
    private DoctorEventListener doctorEventListener;

    @Autowired
    private FullRoomEventListener fullRoomEventListener;

    private EPRuntime epRuntime;

    @PostConstruct
    public void init() {
        final Configuration cepConfig = new Configuration();
        cepConfig.addEventType("puc.esmocyp.DoctorSensorData", DoctorSensorData.class.getName());
        cepConfig.addEventType("puc.esmocyp.LeavingRoomSensorData", LeavingRoomSensorData.class.getName());
        cepConfig.addEventType("puc.esmocyp.EnteringRoomSensorData", EnteringRoomSensorData.class.getName());

        cepConfig.addPlugInSingleRowFunction("gpsIsInArea",
                GPSIsInArea.class.getName(), "gpsIsInArea");

        final EPServiceProvider cep = EPServiceProviderManager
                .getProvider("myCEPEngine", cepConfig);

        epRuntime = cep.getEPRuntime();
        final EPAdministrator cepAdm = cep.getEPAdministrator();

        EPStatement eplDoctor = cepAdm.createEPL("SELECT DISTINCT idSmartphone" +
                "FROM puc.esmocyp.DoctorSensorData.win:time_batch(60 sec) " +
                "WHERE gpsIsInArea(latitude,longitude) = false"
        );

        cepAdm.createEPL(
                "INSERT INTO CountEnteringRoomSensorData " +
                        "SELECT ER.roomId as roomId, count(*) as count " +
                        "FROM puc.esmocyp.EnteringRoomSensorData.win:time(60 sec) as ER " +
                        "GROUP BY ER.roomId"
        );

        cepAdm.createEPL(
                "INSERT INTO CountLeavingRoomSensorData " +
                        "SELECT LR.roomId as roomId, count(*) as count " +
                        "FROM puc.esmocyp.LeavingRoomSensorData.win:time(60 sec) as LR " +
                        "GROUP BY LR.roomId"
        );

        EPStatement eplFullRoom = cepAdm.createEPL(
                "SELECT CER.roomId as roomId " +
                        "FROM CountEnteringRoomSensorData.win:time(60 sec) as CER, " +
                        "       CountLeavingRoomSensorData.win:time(60 sec) as CLR " +
                        "WHERE CER.roomId = CLR.roomId " +
                        "AND CER.count > CLR.count limit 1"
        );

        eplDoctor.addListener( doctorEventListener );
        eplFullRoom.addListener( fullRoomEventListener);
    }

    public EPRuntime getEpRuntime() {
        return epRuntime;
    }
}
