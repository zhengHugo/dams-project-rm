package client;

import DAMS.Frontend.Response.Response;
import DAMS.Frontend.ResponseWrapper.ResponseWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import logger.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import replica.dto.AppointmentType;
import replica.model.appointment.AppointmentAvailability;
import replica.service.admin.Admin;
import replica.service.admin.AdminImplService;

public class AdminClient {

  private Admin adminRemote;
  private final AdminId id;
  private final Logger logger;
  private final Gson gson = new Gson();
  private final Type appointmentAvailabilityListType =
      new TypeToken<List<AppointmentAvailability>>() {}.getType();

  public AdminClient(String id) {
    this.id = new AdminId(id);
    LoggerUtil.createLoggerByClientId(this.id);
    logger = LogManager.getLogger("logger." + id);
    setAdminRemote();
  }

  public Response addAppointment(String id, AppointmentType type, int capacity) {
    boolean success = adminRemote.addAppointment(id, type, capacity);

    String message;
    if (success) {
      message = String.format("Added appointment: %s - %s", type, id);
      logger.info(message);
    } else {
      message = String.format("Unable to add appointment %s - %s", type, id);
      logger.info(message);
    }
    return new Response("AddApointment", "", success, message);
  }

  public Response removeAppointment(String id, AppointmentType type) {
    String message = adminRemote.removeAppointment(id, type);
    logger.info(message);
    if (message.contains("removed")) {
      return new Response("RemoveAppointment", "", true, message);
    } else {
      return new Response("RemoveAppointment", "", false, message);
    }
  }

  public Response listAppointmentAvailability(AppointmentType type) {
    List<AppointmentAvailability> availabilities =
        gson.fromJson(
            adminRemote.listAppointmentAvailability(type), appointmentAvailabilityListType);
    StringBuilder stringBuilder = new StringBuilder("Appointment availabilities of ");
    stringBuilder.append(type).append(" - ");
    for (AppointmentAvailability availability : availabilities) {
      stringBuilder
          .append(availability.getAppointmentId())
          .append(" ")
          .append(availability.getAvailability())
          .append(", ");
    }
    // replace the last ", " with "."
    stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), ".");
    String message = stringBuilder.toString();
    logger.info(message);
    HashMap<String, String> map = new HashMap<>();
    for (AppointmentAvailability aa : availabilities) {
      map.put(aa.getAppointmentId(), String.valueOf(aa.getAvailability()));
    }
    ResponseWrapper wrapper = new ResponseWrapper(map);
    return new Response("ListAppointmentAvailability", "", true, wrapper);
  }

  public static Response getAppointmentTypes() {
    return new Response(
        "GetAppointmentTypes", "", true, new String[] {"Physician, Surgeon, Dental"});
  }

  public static Response getTimeSlots() {
    return new Response("GetTimeSlots", "", true, new String[] {"M", "A", "E"});
  }

  private void setAdminRemote() {
    URL url = null;
    try {
      switch (this.id.getCity()) {
        case Montreal:
          url = new URL("http://localhost:8080/adminservice?wsdl");
          break;
        case Quebec:
          url = new URL("http://localhost:8081/adminservice?wsdl");
          break;
        case Sherbrooke:
          url = new URL("http://localhost:8082/adminservice?wsdl");
          break;
      }
      AdminImplService adminImplService = new AdminImplService(url);
      adminRemote = adminImplService.getAdminImplPort();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
}
