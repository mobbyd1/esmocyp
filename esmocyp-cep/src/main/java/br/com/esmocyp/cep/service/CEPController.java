package br.com.esmocyp.cep.service;

import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import com.espertech.esper.client.*;
import br.com.esmocyp.cep.listeners.DoctorEventListener;
import br.com.esmocyp.cep.listeners.FullRoomEventListener;
import br.com.esmocyp.messaging.model.DoctorSensorData;
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
        cepConfig.addEventType("DoctorSensorData", DoctorSensorData.class.getName());
        cepConfig.addEventType("LeavingRoomSensorData", LeavingRoomSensorData.class.getName());
        cepConfig.addEventType("EnteringRoomSensorData", EnteringRoomSensorData.class.getName());

        cepConfig.addPlugInSingleRowFunction("gpsIsInArea",
                GPSIsInArea.class.getName(), "gpsIsInArea");

        final EPServiceProvider cep = EPServiceProviderManager
                .getProvider("myCEPEngine", cepConfig);

        epRuntime = cep.getEPRuntime();
        final EPAdministrator cepAdm = cep.getEPAdministrator();

        EPStatement eplDoctor = cepAdm.createEPL("SELECT DISTINCT idSmartphone " +
                "FROM DoctorSensorData.win:time_batch(1 sec) " +
                "WHERE gpsIsInArea(latitude, longitude) = false"
        );

        cepAdm.createEPL(
                "INSERT INTO CountEnteringRoomSensorData " +
                        "SELECT ER.roomId as roomId, count(*) as count " +
                        "FROM EnteringRoomSensorData.win:time(1 sec) as ER " +
                        "GROUP BY ER.roomId"
        );

        cepAdm.createEPL(
                "INSERT INTO CountLeavingRoomSensorData " +
                        "SELECT LR.roomId as roomId, count(*) as count " +
                        "FROM LeavingRoomSensorData.win:time(1 sec) as LR " +
                        "GROUP BY LR.roomId"
        );

        EPStatement eplFullRoom = cepAdm.createEPL(
                "SELECT CER.count as enteringCount" +
                        " , CER.roomId as enteringRoomId" +
                        " , CLR.count as leavingCount" +
                        " , CLR.roomId as  leavingRoomId " +
                        "FROM CountEnteringRoomSensorData.win:time(1 sec) as CER " +
                        "LEFT OUTER JOIN " +
                        "       CountLeavingRoomSensorData.win:time(1 sec) as CLR " +
                        "ON CER.roomId = CLR.roomId "
        );

        eplDoctor.addListener( doctorEventListener );
        eplFullRoom.addListener( fullRoomEventListener);
    }

    public EPRuntime getEpRuntime() {
        return epRuntime;
    }
}
