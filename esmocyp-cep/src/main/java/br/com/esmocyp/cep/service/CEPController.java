package br.com.esmocyp.cep.service;

import br.com.esmocyp.cep.listeners.DoctorEventListenerDLO;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLO;
import br.com.esmocyp.cep.model.DoctorSensorData;
import br.com.esmocyp.cep.model.EnteringRoomSensorData;
import br.com.esmocyp.cep.model.LeavingRoomSensorData;
import com.espertech.esper.client.*;
import br.com.esmocyp.cep.listeners.FullRoomEventListenerDLOImpl;
import br.com.esmocyp.cep.util.GPSIsInArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ruhandosreis on 16/08/17.
 *
 * This class is responsible to initialize the cep rules.
 */
@Component
public class CEPController {

    @Autowired
    private DoctorEventListenerDLO doctorEventListener;

    @Autowired
    private FullRoomEventListenerDLO fullRoomEventListener;

    private EPRuntime epRuntime;

    private EPServiceProvider cep;

    /**
     * Initialize the cep rules
     */
    @PostConstruct
    public void init() {
        final Configuration cepConfig = new Configuration();

        // define the event types
        cepConfig.addEventType("DoctorSensorData", DoctorSensorData.class.getName());
        cepConfig.addEventType("LeavingRoomSensorData", LeavingRoomSensorData.class.getName());
        cepConfig.addEventType("EnteringRoomSensorData", EnteringRoomSensorData.class.getName());

        // custom method to use in the queries
        cepConfig.addPlugInSingleRowFunction("gpsIsInArea",
                GPSIsInArea.class.getName(), "gpsIsInArea");

        cep = EPServiceProviderManager
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
                    "ON CDNH.idSmartphone = CDH.idSmartphone " +
                    "WHERE CDNH.count <> 0 "
        );

        cepAdm.createEPL(
                "INSERT INTO CountEnteringRoomSensorData " +
                        "SELECT ER.roomId as roomId, count(*) as count " +
                        "FROM EnteringRoomSensorData.win:time_batch(10 sec) as ER " +
                        "GROUP BY ER.roomId"
        );

        cepAdm.createEPL(
                "INSERT INTO CountLeavingRoomSensorData " +
                        "SELECT LR.roomId as roomId, count(*) as count " +
                        "FROM LeavingRoomSensorData.win:time_batch(10 sec) as LR " +
                        "GROUP BY LR.roomId"
        );

        EPStatement eplFullRoom = cepAdm.createEPL(
                "SELECT CER.count as enteringCount" +
                        " , CER.roomId as enteringRoomId" +
                        " , CLR.count as leavingCount " +
                        "FROM CountEnteringRoomSensorData.win:length(1) as CER " +
                        "LEFT OUTER JOIN " +
                        "       CountLeavingRoomSensorData.win:length(1) as CLR " +
                        "ON CER.roomId = CLR.roomId " +
                        "WHERE CER.count <> 0 "
        );

        // adding the listeners for the query result processing
        eplDoctor.addListener( doctorEventListener );
        eplFullRoom.addListener( fullRoomEventListener);

    }

    /**
     * Gets the event processing runtime
     * @return
     */
    public EPRuntime getEpRuntime() {
        return epRuntime;
    }

    /**
     * Finalize the cep rules
     */
    public void destroy() {
        if( cep != null ) {
            final EPAdministrator epAdministrator = cep.getEPAdministrator();
            epAdministrator.destroyAllStatements();

            cep.destroy();
        }
    }
}
