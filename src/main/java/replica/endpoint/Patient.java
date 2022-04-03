package replica.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebService;
import replica.dto.AppointmentType;

@WebService
public interface Patient {

  @WebMethod
  boolean bookAppointment(String patientId, AppointmentType type, String appointmentId);

  @WebMethod
  boolean bookLocalAppointment(String patientId, AppointmentType type, String appointmentId);

  @WebMethod
  String getAppointmentSchedule(String patientId);

  @WebMethod
  String getLocalAppointmentSchedule(String patientId);

  @WebMethod
  boolean cancelAppointment(String patientId, AppointmentType type, String appointmentId);

  @WebMethod
  boolean cancelLocalAppointment(String patientId, AppointmentType type, String appointmentId);

  @WebMethod
  boolean swapAppointment(String patientId, AppointmentType oldType, String oldAppointmentId,
      AppointmentType newType, String newAppointmentId);

  @WebMethod
  String[] getAppointmentTypes();

  @WebMethod
  String[] getTimeSlots();
}
