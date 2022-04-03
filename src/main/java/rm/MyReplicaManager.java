package rm;

import DAMS.Frontend.Request.Request;
import client.AdminClient;
import replica.dto.AppointmentType;

public class MyReplicaManager extends ReplicaManager{
  AdminClient mtlAdmin = new AdminClient("MTLA1111");
  AdminClient queAdmin = new AdminClient("QUEA1111");

  public void handleRequest(Request request){
    switch (request.getOperation()) {
      case "MTL AddAppointment":
        mtlAdmin.addAppointment(
            request.getAppointmentID(),
            AppointmentType.fromValue(request.getAppointmentType()),
            request.getCapacity());
        break;
      case "QUE AddAppointment":
        break;
      case "SHE AddAppointment":
        break;
      default:
        // TODO
    }
  }

}
