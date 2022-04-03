package replica.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.jws.WebService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import replica.common.GlobalConstants;
import replica.database.Database;
import replica.dto.AppointmentType;
import replica.model.PatientId;
import replica.model.appointment.Appointment;
import replica.model.appointment.AppointmentId;
import replica.service.patient.Patient;
import replica.service.patient.PatientImplService;

@WebService(endpointInterface = "replica.endpoint.Patient")
public class PatientImpl implements replica.endpoint.Patient {

  private final Database database = Database.getInstance();
  private static final Logger logger = LogManager.getLogger();

  private final Gson gson = new Gson();
  private final Type appointmentListType = new TypeToken<List<Appointment>>() {
  }.getType();
  private Patient patientRemoteMtl;
  private Patient patientRemoteQue;
  private Patient patientRemoteShe;

  @Override
  public boolean bookAppointment(String patientIdStr, AppointmentType type,
      String appointmentIdStr) {
    setPatientRemote();

    PatientId patientId = new PatientId(patientIdStr);
    AppointmentId appointmentId = new AppointmentId(appointmentIdStr);
    List<Appointment> patientAppointments =
        gson.fromJson(getAppointmentSchedule(patientIdStr), appointmentListType);

    if (patientAppointments.stream()
        .filter(app -> app.getType().equals(type))
        .map(app -> app.getAppointmentId().getDateString())
        .collect(Collectors.toList())
        .contains(appointmentId.getDateString())) {
      logger.info(
          String.format(
              "The patient %s already has an appointment %s - %s. Booking not proceeded",
              patientId, type, appointmentId));
      return false;
    }
    if (GlobalConstants.thisCity.equals(appointmentId.getCity())) {
      // patient book an appointment in its own city
      return bookLocalAppointment(patientIdStr, type, appointmentIdStr);
    } else {
      // call bookLocalAppointment at the city according to the appointment
      // check the 3 times/week booking limit first
      DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyy");
      int thisWeek = DateTime.parse(appointmentId.getDateString(), formatter).getWeekOfWeekyear();
      long allAppsInThisWeekCount =
          patientAppointments.stream()
              .filter(
                  app ->
                      DateTime.parse(app.getAppointmentId().getDateString(), formatter)
                          .getWeekOfWeekyear()
                          == thisWeek)
              .count();
      List<Appointment> localPatientAppointments =
          gson.fromJson(getLocalAppointmentSchedule(patientIdStr), appointmentListType);
      long localAppsInThisWeekCount =
          localPatientAppointments.stream()
              .filter(
                  app ->
                      DateTime.parse(app.getAppointmentId().getDateString(), formatter)
                          .getWeekOfWeekyear()
                          == thisWeek)
              .count();
      long nonlocalAppCount = allAppsInThisWeekCount - localAppsInThisWeekCount;
      if (nonlocalAppCount < 3) {
        logger.info(
            String.format(
                "Call server %s to book appointment for %s, %s - %s",
                appointmentId.getCity().code, patientId, type, appointmentId));
        switch (patientId.getCity()) {
          case Montreal:
            return patientRemoteMtl.bookLocalAppointment(patientIdStr, type, appointmentIdStr);
          case Quebec:
            return patientRemoteQue.bookLocalAppointment(patientIdStr, type, appointmentIdStr);
          case Sherbrooke:
            return patientRemoteShe.bookLocalAppointment(patientIdStr, type, appointmentIdStr);

        }
      } else {
        logger.info(
            String.format(
                "Non-local appointments over limit. Cannot book for %s, %s - %s",
                patientId, type, appointmentId));
        return false;
      }
    }
    return false;
  }

  @Override
  public synchronized boolean bookLocalAppointment(String patientIdStr, AppointmentType type,
      String appointmentIdStr) {
    PatientId patientId = new PatientId(patientIdStr);
    AppointmentId appointmentId = new AppointmentId(appointmentIdStr);

    Optional<Appointment> appointmentOptional = database.findByTypeAndId(type, appointmentId);
    if (appointmentOptional.isPresent()) {
      Appointment appointment = appointmentOptional.get();
      if (appointment.addPatient(patientId) && database.update(appointment, type)) {
        logger.info(String.format("Book appointment success: %s, %s - %s", patientId.getId(), type,
            appointmentId.getId()));
        return true;
      } else {
        logger.info(String.format("Cannot book appointment: %s, %s - %s", patientId.getId(), type,
            appointmentId.getId()));
        return false;
      }
    } else {
      logger.info(String.format("The appointment %s - %s doesn't exist.", type, appointmentId));
      return false;
    }
  }

  @Override
  public String getAppointmentSchedule(String patientIdStr) {
    setPatientRemote();
    List<Appointment> allAppointment =
        new ArrayList<>(
            gson.fromJson(getLocalAppointmentSchedule(patientIdStr), appointmentListType));
    Patient patientRemote1 = null;
    Patient patientRemote2 = null;
    switch (GlobalConstants.thisCity) {
      case Montreal:
        patientRemote1 = patientRemoteQue;
        patientRemote2 = patientRemoteShe;
        break;
      case Quebec:
        patientRemote1 = patientRemoteMtl;
        patientRemote2 = patientRemoteShe;
        break;
      case Sherbrooke:
        patientRemote1 = patientRemoteMtl;
        patientRemote2 = patientRemoteQue;
        break;
    }
    allAppointment.addAll(
        gson.fromJson(
            patientRemote1.getLocalAppointmentSchedule(patientIdStr), appointmentListType));
    allAppointment.addAll(
        gson.fromJson(
            patientRemote2.getLocalAppointmentSchedule(patientIdStr), appointmentListType));
    return gson.toJson(allAppointment);
  }

  @Override
  public String getLocalAppointmentSchedule(String patientId) {
    List<Appointment> appointmentList = database.findAllByPatientId(new PatientId(patientId));
    return gson.toJson(appointmentList);
  }

  @Override
  public boolean cancelAppointment(String patientIdStr, AppointmentType type,
      String appointmentIdStr) {
    AppointmentId appointmentId = new AppointmentId(appointmentIdStr);
    if (appointmentId.getCity().equals(GlobalConstants.thisCity)) {
      return cancelLocalAppointment(patientIdStr, type, appointmentIdStr);
    } else {
      setPatientRemote();
      Patient patientRemote = null;
      switch (appointmentId.getCity()) {
        case Montreal:
          patientRemote = patientRemoteMtl;
          break;
        case Quebec:
          patientRemote = patientRemoteQue;
          break;
        case Sherbrooke:
          patientRemote = patientRemoteShe;
          break;
      }
      if (patientRemote.cancelLocalAppointment(patientIdStr, type, appointmentIdStr)) {
        logger.info(
            String.format(
                "Cancel appointment success: %s, %s - %s", patientIdStr, type, appointmentId));
        return true;
      } else {
        logger.info(
            String.format(
                "The patient %s doesn't have an appointment with %s - %s",
                patientIdStr, type, appointmentId));
        return false;
      }
    }

  }

  @Override
  public boolean cancelLocalAppointment(String patientIdStr, AppointmentType type,
      String appointmentIdStr) {
    AppointmentId appointmentId = new AppointmentId(appointmentIdStr);
    PatientId patientId = new PatientId(patientIdStr);

    Optional<Appointment> appointmentOptional = database.findByTypeAndId(type, appointmentId);
    if (appointmentOptional.isPresent()) {
      Appointment appointment = appointmentOptional.get();
      if (appointment.removePatient(patientId)) {
        database.update(appointment, type);
        logger.info(String.format("Cancel appointment success: %s, %s - %s", patientId, type,
            appointmentId));
        return true;
      } else {
        logger.info(
            String.format("The patient %s doesn't have an appointment with %s - %s", patientId,
                type, appointmentId));
        return false;
      }
    } else {
      logger.info(String.format("The appointment %s doesn't exist", appointmentId));
      return false;
    }
  }

  @Override
  public boolean swapAppointment(String patientIdStr, AppointmentType oldType,
      String oldAppointmentId, AppointmentType newType, String newAppointmentId) {
    if (cancelAppointment(patientIdStr, oldType, oldAppointmentId)) {
      if (bookAppointment(patientIdStr, newType, newAppointmentId)) {
        logger.info(
            String.format("Swapped appointment for %s: from %s - %s to %s - %s", patientIdStr,
                oldType, oldAppointmentId, newType, newAppointmentId));
        return true;
      } else {
        logger.info(String.format("Cannot book new appointment for %s: %s - %s. Swap not proceeded",
            patientIdStr, newType, newAppointmentId));
        bookAppointment(patientIdStr, oldType, oldAppointmentId);
        return false;
      }
    } else {
      logger.info(String.format("Cannot cancel old appointment for %s: %s - %s. Swap not proceeded",
          patientIdStr, oldType, oldAppointmentId));
      return false;
    }
  }

  @Override
  public String[] getAppointmentTypes() {
    return (String[]) database.keySet().stream().map(Enum::toString).toArray();
  }

  @Override
  public String[] getTimeSlots() {
    return new String[] {"M", "A", "E"};
  }

  private void setPatientRemote() {
    if (patientRemoteMtl != null || patientRemoteShe != null) {
      return;
    }

    URL url1;
    URL url2;
    try {
      switch (GlobalConstants.thisCity) {
        case Montreal:
          url1 = new URL("http://localhost:8081/patientservice?wsdl");
          url2 = new URL("http://localhost:8082/patientservice?wsdl");
          patientRemoteQue = new PatientImplService(url1).getPatientImplPort();
          patientRemoteShe = new PatientImplService(url2).getPatientImplPort();
          break;
        case Quebec:
          url1 = new URL("http://localhost:8080/patientservice?wsdl");
          url2 = new URL("http://localhost:8082/patientservice?wsdl");
          patientRemoteMtl = new PatientImplService(url1).getPatientImplPort();
          patientRemoteShe = new PatientImplService(url2).getPatientImplPort();
          break;
        case Sherbrooke:
          url1 = new URL("http://localhost:8080/patientservice?wsdl");
          url2 = new URL("http://localhost:8081/patientservice?wsdl");
          patientRemoteMtl = new PatientImplService(url1).getPatientImplPort();
          patientRemoteQue = new PatientImplService(url2).getPatientImplPort();
          break;
      }

    } catch (
        MalformedURLException e) {
      e.printStackTrace();
    }
  }
}
