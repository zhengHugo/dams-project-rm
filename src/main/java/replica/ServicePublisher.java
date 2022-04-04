package replica;

import DAMS.Frontend.Request.Request;
import DAMS.Frontend.Response.Response;
import client.AdminClient;
import client.PatientClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import javax.xml.ws.Endpoint;
import replica.common.GlobalConstants;
import replica.dto.AppointmentType;
import replica.impl.AdminImpl;
import replica.impl.PatientImpl;
import replica.model.City;

public class ServicePublisher {

  public static void main(String[] args) {
    int port;
    switch (args[0]) {
      case "mtl":
        GlobalConstants.thisCity = City.Montreal;

        // publish ws
        Endpoint.publish("http://localhost:8080/adminservice", new AdminImpl());
        Endpoint.publish("http://localhost:8080/patientservice", new PatientImpl());

        // listen on udp
        port = 6821;
        getRequestAndSendResponse(port);

        break;
      case "que":
        GlobalConstants.thisCity = City.Quebec;
        Endpoint.publish("http://localhost:8081/adminservice", new AdminImpl());
        Endpoint.publish("http://localhost:8081/patientservice", new PatientImpl());

        port = 6822;
        getRequestAndSendResponse(port);
        break;
      case "she":
        GlobalConstants.thisCity = City.Sherbrooke;
        Endpoint.publish("http://localhost:8082/adminservice", new AdminImpl());
        Endpoint.publish("http://localhost:8082/patientservice", new PatientImpl());

        port = 6823;
        getRequestAndSendResponse(port);
        break;
      default:
        System.out.println("Invalid arg...");
    }
  }

  private static void getRequestAndSendResponse(int port) {
    final String HOST_IP = "192.168.2.12";
    final int PORT = 6800;
    DatagramSocket udpSocket = null;
    try {
      udpSocket = new DatagramSocket(port);
      byte[] buf = new byte[32767];
      DatagramPacket requestPacket = new DatagramPacket(buf, buf.length);
      udpSocket.receive(requestPacket);
      byte[] requestData = requestPacket.getData();
      ObjectInputStream objectInputStream =
          new ObjectInputStream(new ByteArrayInputStream(requestData));
      Request request = (Request) objectInputStream.readObject();
      Response response = handleRequest(request);

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
      objectOutputStream.writeObject(response);
      objectOutputStream.flush();
      byte[] responseAsBytes = byteArrayOutputStream.toByteArray();
      InetSocketAddress ip = new InetSocketAddress(HOST_IP, PORT);
      DatagramPacket responsePacket =
          new DatagramPacket(responseAsBytes, responseAsBytes.length, ip);
      udpSocket.send(responsePacket);

    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static Response handleRequest(Request request) {
    AdminClient adminClient;
    PatientClient patientClient;
    switch (request.getOperation()) {
      case "AddAppointment":
        adminClient = new AdminClient(request.getUserID());
        return adminClient.addAppointment(
            request.getAppointmentID(),
            AppointmentType.fromValue(request.getAppointmentType()),
            request.getCapacity());
      case "RemoveAppointment":
        adminClient = new AdminClient(request.getUserID());
        return adminClient.removeAppointment(
            request.getAppointmentID(), AppointmentType.fromValue(request.getAppointmentType()));
      case "ListAppointmentAvailability":
        adminClient = new AdminClient(request.getUserID());
        return adminClient.listAppointmentAvailability(
            AppointmentType.fromValue(request.getAppointmentType()));
      case "GetAppointmentTypes":
        return AdminClient.getAppointmentTypes();
      case "GetTimeSlots":
        return AdminClient.getTimeSlots();
      case "BookAppointment":
        patientClient = new PatientClient(request.getPatientID());
        return patientClient.bookAppointment(
            request.getAppointmentID(), AppointmentType.fromValue(request.getOldAppointmentType()));
      case "GetAppointmentSchedule":
        patientClient = new PatientClient(request.getPatientID());
        return patientClient.getAppointmentSchedule();
      case "CancelAppointment":
        patientClient = new PatientClient(request.getPatientID());
        return patientClient.cancelAppointment(
            request.getAppointmentID(), AppointmentType.fromValue(request.getAppointmentType()));
      case "SwapAppointment":
        patientClient = new PatientClient(request.getPatientID());
        return patientClient.swapAppointment(
            request.getOldAppointmentID(),
            AppointmentType.fromValue(request.getOldAppointmentType()),
            request.getAppointmentID(),
            AppointmentType.fromValue(request.getAppointmentType()));
      default:
        System.out.println("Invalid operation");
        return null;
    }
  }
}
