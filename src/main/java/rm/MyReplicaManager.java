package rm;

import DAMS.Frontend.Request.Request;
import client.AdminClient;
import client.PatientClient;
import replica.dto.AppointmentType;

public class MyReplicaManager extends ReplicaManager {
  AdminClient adminClient;
  PatientClient patientClient;

  @Override
  public void handleRequest(Request request) {
    switch (request.getOperation()) {
      case "AddAppointment":
        adminClient = new AdminClient(request.getAdminId());
        adminClient.addAppointment(
            request.getAppointmentID(),
            AppointmentType.fromValue(request.getAppointmentType()),
            request.getCapacity());
        break;
      case "RemoveAppointment":
        adminClient = new AdminClient(request.getAdminId());
        adminClient.removeAppointment(
            request.getAppointmentID(), AppointmentType.fromValue(request.getAppointmentType()));
        break;
      case "ListAppointmentAvailability":
        adminClient = new AdminClient(request.getAdminId());
        adminClient.listAppointmentAvailability(
            AppointmentType.fromValue(request.getAppointmentType()));
        break;
      case "BookAppointment":
        patientClient = new PatientClient(request.getPatientID());
        patientClient.bookAppointment(
            request.getAppointmentID(), AppointmentType.fromValue(request.getOldAppointmentType()));
        break;
      case "GetAppointmentSchedule":
        patientClient = new PatientClient(request.getPatientID());
        patientClient.getAppointmentSchedule();
        break;
      case "CancelAppointment":
        patientClient = new PatientClient(request.getPatientID());
        patientClient.cancelAppointment(
            request.getAppointmentID(), AppointmentType.fromValue(request.getAppointmentType()));
        break;
      case "SwapAppointment":
        patientClient = new PatientClient(request.getPatientID());
        patientClient.swapAppointment(
            request.getOldAppointmentID(),
            AppointmentType.fromValue(request.getOldAppointmentType()),
            request.getAppointmentID(),
            AppointmentType.fromValue(request.getAppointmentType()));
        break;
      default:
        // TODO: invalid operation
    }
  }
}
