package client;

import DAMS.Frontend.Response.Response;
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
  private final Type appointmentListType = new TypeToken<List<Appointment>>() {}.getType();

  public PatientClient(String id) {
    this.id = new PatientId(id);
    LoggerUtil.createLoggerByClientId(this.id);
    logger = LogManager.getLogger("logger." + id);
    setPatientRemote();
  }

  public Response bookAppointment(String appointmentIdStr, AppointmentType type) {
    boolean success = patientRemote.bookAppointment(this.id.getId(), type, appointmentIdStr);
    String message;
    if (success) {
      message = String.format("Booked appointment: %s - %s", type, appointmentIdStr);
      logger.info(message);
    } else {
      message = String.format("Unable to book appointment: %s - %s", type, appointmentIdStr);
      logger.info(message);
    }
    return new Response("BookAppointment", "", success, message);
  }

  public Response getAppointmentSchedule() {
    List<Appointment> appointments =
        gson.fromJson(patientRemote.getAppointmentSchedule(this.id.getId()), appointmentListType);
    String message =
        String.format(
            "Get appointment schedule: %s",
            appointments.stream()
                .map(app -> app.getType() + " - " + app.getAppointmentId().getId() + " ")
                .reduce(String::concat)
                .orElse(""));
    logger.info(message);
    return new Response("GetAppointmentSchedule", "", true, message);
  }

  public Response cancelAppointment(String id, AppointmentType type) {
    if (patientRemote.cancelAppointment(this.id.getId(), type, id)) {
      String message = "Cancel appointment success";
      logger.info(message);
      return new Response("CancelAppointment", "", true, message);
    } else {
      String message = "Cannot canncel appointment";
      logger.info(message);
      return new Response("CancelAppointment", "", false, message);
    }
  }

  public Response swapAppointment(
      String oldId, AppointmentType oldType, String newId, AppointmentType newType) {
    if (patientRemote.swapAppointment(this.id.getId(), oldType, oldId, newType, newId)) {
      String message = "Swap appointment success";
      logger.info(message);
      return new Response("SwapAppointment", "", true, message);
    } else {
      String message = "Cannot swap appointment";
      logger.info(message);
      return new Response("SwapAppointment", "", false, message);
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
