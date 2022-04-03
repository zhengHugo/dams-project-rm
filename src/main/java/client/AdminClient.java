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
import replica.model.appointment.AppointmentAvailability;
import replica.service.admin.Admin;
import replica.service.admin.AdminImplService;

public class AdminClient {

  private Admin adminRemote;
  private final AdminId id;
  private final Logger logger;
  private final Gson gson = new Gson();
  private final Type appointmentAvailabilityListType =
      new TypeToken<List<AppointmentAvailability>>() {
      }.getType();

  public AdminClient(String id) {
    this.id = new AdminId(id);
    LoggerUtil.createLoggerByClientId(this.id);
    logger = LogManager.getLogger("logger." + id);
    setAdminRemote();
  }

  public void addAppointment(String id, AppointmentType type, int capacity) {
    boolean success = adminRemote.addAppointment(id, type, capacity);
    if (success) {
      logger.info(String.format("Added appointment: %s - %s", type, id));
    } else {
      logger.info(String.format("Unable to add appointment %s - %s", type, id));
    }
  }

  public void removeAppointment(String id, AppointmentType type) {
    String message = adminRemote.removeAppointment(id, type);
    logger.info(message);
  }

  public void listAppointmentAvailability(AppointmentType type) {
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
    logger.info(stringBuilder.toString());
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
