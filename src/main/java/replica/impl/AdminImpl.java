package replica.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.jws.WebService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import replica.common.GlobalConstants;
import replica.database.Database;
import replica.dto.AppointmentType;
import replica.model.PatientId;
import replica.model.appointment.Appointment;
import replica.model.appointment.AppointmentAvailability;
import replica.model.appointment.AppointmentId;
import replica.service.admin.Admin;
import replica.service.admin.AdminImplService;

@WebService(endpointInterface = "replica.endpoint.Admin")
public class AdminImpl implements replica.endpoint.Admin {

  private final Database database = Database.getInstance();
  private static final Logger logger = LogManager.getLogger();
  private final Gson gson = new Gson();
  private final Type appointmentAvailabilityListType =
      new TypeToken<List<AppointmentAvailability>>() {}.getType();
  private Admin adminRemote1;
  private Admin adminRemote2;

  @Override
  public synchronized boolean addAppointment(
      String appointmentId, AppointmentType type, int capacity) {
    AppointmentId id = new AppointmentId(appointmentId);
    if (!database.findByTypeAndId(type, id).isPresent()) {
      database.add(id, type, capacity);
      logger.info(String.format("Appointment is added: %s, %s", type, id.getId()));
      return true;
    } else {
      logger.info(
          String.format(
              "Appointment is not added because appointment already exists with type: %s, id: %s",
              type, id.getId()));
      return false;
    }
  }

  @Override
  public synchronized String removeAppointment(String id, AppointmentType type) {
    AtomicReference<String> message = new AtomicReference<>();
    AppointmentId appointmentId = new AppointmentId(id);
    Optional<Appointment> appointmentOptional = database.findByTypeAndId(type, appointmentId);
    if (appointmentOptional.isPresent()) {
      Appointment appointment = appointmentOptional.get();
      List<PatientId> patientIds = appointment.getPatientIds();
      if (patientIds.size() > 0) {
        message.set(putPatientsAfterAppointment(patientIds, type, appointmentId));
      }
      database.remove(appointmentId, type);
      message.set("The target appointment is removed.");
      logger.info(message.get());
    } else {
      message.set("The target appointment does not exist.");
      logger.info(message.get());
    }
    return message.get();
  }

  @Override
  public String listAppointmentAvailability(AppointmentType type) {
    setAdminRemote();

    List<AppointmentAvailability> availabilities =
        new ArrayList<>(
            gson.fromJson(listLocalAppointmentAvailability(type), appointmentAvailabilityListType));
    assert adminRemote1 != null;
    availabilities.addAll(
        gson.fromJson(
            adminRemote1.listLocalAppointmentAvailability(type), appointmentAvailabilityListType));
    assert adminRemote2 != null;
    availabilities.addAll(
        gson.fromJson(
            adminRemote2.listLocalAppointmentAvailability(type), appointmentAvailabilityListType));
    return gson.toJson(availabilities);
  }

  @Override
  public String listLocalAppointmentAvailability(AppointmentType type) {
    List<AppointmentAvailability> availabilities =
        database.findAllByType(type).stream()
            .map(
                appointment ->
                    new AppointmentAvailability(
                        appointment.getAppointmentId().getId(), appointment.getRemainingCapacity()))
            .collect(Collectors.toList());
    return gson.toJson(availabilities);
  }

  @Override
  public String[] getAppointmentTypes() {
    return (String[]) database.keySet().stream().map(Enum::toString).toArray();
  }

  @Override
  public String[] getTimeSlots() {
    return new String[] {"M", "A", "E"};
  }

  private synchronized String putPatientsAfterAppointment(
      List<PatientId> patientIds, AppointmentType type, AppointmentId id) {
    Optional<Appointment> targetAppointmentOptional = database.findByTypeAndId(type, id);
    String message = "";
    if (targetAppointmentOptional.isPresent()) {
      Optional<AppointmentId> nextAppointmentIdOptional = database.findNextAppointmentId(type, id);
      if (nextAppointmentIdOptional.isPresent()) {
        AppointmentId nextAppId = nextAppointmentIdOptional.get();
        Appointment nextApp = database.findByTypeAndId(type, nextAppId).get();
        if (patientIds.size() > nextApp.getRemainingCapacity()) {
          // next appointment cannot fit all patients; try put them to later ones
          int fromIndex = nextApp.getRemainingCapacity();
          nextApp.addPatients(patientIds.subList(0, fromIndex));
          message =
              String.format(
                  "Patient(s) %s are assigned to appointment %s:%s",
                  patientIds.subList(0, fromIndex), type, id);
          logger.info(message);
          putPatientsAfterAppointment(
              patientIds.subList(fromIndex, patientIds.size()), type, nextAppId);
        } else {
          nextApp.addPatients(patientIds);
          message =
              String.format(
                  "Patient(s) %s are assigned to appointment %s:%s", patientIds, type, id);
          logger.info(message);
        }
      } else {
        // this is the last appointment; no future appointment is available
        message =
            "Patient(s) %s are dropped from appointments because no future appointments are available";
        logger.warn(message);
      }
    }
    return message;
  }

  private void setAdminRemote() {
    if (adminRemote1 != null) {
      return;
    }

    URL url1 = null;
    URL url2 = null;
    try {
      switch (GlobalConstants.thisCity) {
        case Montreal:
          url1 = new URL("http://localhost:8081/adminservice?wsdl");
          url2 = new URL("http://localhost:8082/adminservice?wsdl");
          break;
        case Quebec:
          url1 = new URL("http://localhost:8080/adminservice?wsdl");
          url2 = new URL("http://localhost:8082/adminservice?wsdl");
          break;
        case Sherbrooke:
          url1 = new URL("http://localhost:8080/adminservice?wsdl");
          url2 = new URL("http://localhost:8081/adminservice?wsdl");
          break;
      }
      adminRemote1 = new AdminImplService(url1).getAdminImplPort();
      adminRemote2 = new AdminImplService(url2).getAdminImplPort();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
}
