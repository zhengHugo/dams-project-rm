package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import logger.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import replica.dto.AppointmentType;
import replica.model.appointment.Appointment;
import replica.service.patient.Patient;
import replica.service.patient.PatientImplService;

public class PatientClient {

  private final PatientId id;
  private Patient patientRemote;
  private final Logger logger;
  private final Gson gson = new Gson();
  private final Type appointmentListType = new TypeToken<List<Appointment>>() {
  }.getType();

  public PatientClient(String id) {
    this.id = new PatientId(id);
    LoggerUtil.createLoggerByClientId(this.id);
    logger = LogManager.getLogger("logger." + id);
    setPatientRemote();
  }

  public void bookAppointment(String appointmentIdStr, AppointmentType type) {
    boolean success =
        patientRemote.bookAppointment(this.id.getId(), type, appointmentIdStr);
    if (success) {
      logger.info(String.format("Booked appointment: %s - %s", type, appointmentIdStr));
    } else {
      logger.info(String.format("Unable to book appointment: %s - %s", type, appointmentIdStr));
    }
  }

  public List<Appointment> getAppointmentSchedule() {
    List<Appointment> appointments =
        gson.fromJson(patientRemote.getAppointmentSchedule(this.id.getId()), appointmentListType);
    logger.info(
        String.format(
            "Get appointment schedule: %s",
            appointments.stream()
                .map(app -> app.getType() + " - " + app.getAppointmentId().getId() + " ")
                .reduce(String::concat)
                .orElse("")));
    return appointments;
  }

  public boolean cancelAppointment(String id, AppointmentType type) {
    if (patientRemote.cancelAppointment(this.id.getId(), type, id)) {
      logger.info("Cancel appointment success");
      return true;
    } else {
      logger.info("Cannot canncel appointment");
      return false;
    }
  }

  public boolean swapAppointment(
      String oldId, AppointmentType oldType, String newId, AppointmentType newType) {
    if (patientRemote.swapAppointment(
        this.id.getId(), oldType, oldId, newType, newId)) {
      logger.info("Swap appointment success");
      return true;
    } else {
      logger.info("Cannot swap appointment");
      return false;
    }
  }

  private void setPatientRemote() {
    URL url = null;
    try {
      switch (this.id.getCity()) {
        case Montreal:
          url = new URL("http://localhost:8080/patientservice?wsdl");
          break;
        case Quebec:
          url = new URL("http://localhost:8081/patientservice?wsdl");
          break;
        case Sherbrooke:
          url = new URL("http://localhost:8082/patientservice?wsdl");
          break;
      }
      PatientImplService patientImplService = new PatientImplService(url);
      patientRemote = patientImplService.getPatientImplPort();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
}
