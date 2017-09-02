package br.com.esmocyp.cep.service;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.model.DoctorSensorData;
import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import com.espertech.esper.client.*;
import br.com.esmocyp.cep.listeners.DoctorEventListenerDLOImpl;
import br.com.esmocyp.cep.listeners.FullRoomEventListener;
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
    private DoctorEventListenerDLO doctorEventListener;

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

        cepAdm.createEPL(
                "INSERT INTO CountDoctorNotInHospital " +
                    "SELECT DSD.idSmartphone as idSmartphone, count(*) as count " +
                    "FROM DoctorSensorData.win:time_batch(10 sec) as DSD " +
                    "WHERE gpsIsInArea(latitude, longitude) = false " +
                    "GROUP BY DSD.idSmartphone" );

        cepAdm.createEPL(
                "INSERT INTO CountDoctorInHospital " +
                        "SELECT DSD.idSmartphone as idSmartphone, count(*) as count " +
                        "FROM DoctorSensorData.win:time_batch(10 sec) as DSD " +
                        "WHERE gpsIsInArea(latitude, longitude) = true " +
                        "GROUP BY DSD.idSmartphone" );

        EPStatement eplDoctor = cepAdm.createEPL(
                "SELECT  CDNH.idSmartphone as idSmartphone " +
                        ", CDNH.count as notInHospitalCount " +
                        ", CDH.count as inHospitalCount " +
                    "FROM CountDoctorNotInHospital.win:length(1) CDNH " +
                    "LEFT OUTER JOIN " +
                    "   CountDoctorInHospital.win:length(1) CDH " +
                    "ON CDNH.idSmartphone = CDH.idSmartphone"
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
                        "FROM CountEnteringRoomSensorData.win:length(1) as CER " +
                        "LEFT OUTER JOIN " +
                        "       CountLeavingRoomSensorData.win:length(1) as CLR " +
                        "ON CER.roomId = CLR.roomId "
        );

        eplDoctor.addListener( doctorEventListener );
        eplFullRoom.addListener( fullRoomEventListener);
    }

    public EPRuntime getEpRuntime() {
        return epRuntime;
    }
}
