package rm;

import DAMS.Frontend.Request.Request;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public abstract class ReplicaManager implements Runnable {
  private MulticastSocket multicastSocket;
  private final PriorityQueue<Request> holdBackQueue;
  private int nextSeqNum;

  public ReplicaManager() {
    this.holdBackQueue = new PriorityQueue<>(Comparator.comparingInt(Request::getSequenceNumber));
    this.nextSeqNum = 1;
  }

  @Override
  public void run() {
    try {
      this.multicastSocket = new MulticastSocket(1421);
      // TODO: group ip address
      InetAddress group = InetAddress.getByName("230.0.0.1");
      multicastSocket.joinGroup(group);
      while (true) {
        Request incomingRequest = this.receive();
        System.out.println("Incoming request seqnum:  " + incomingRequest.getSequenceNumber());
        holdBackQueue.add(incomingRequest);
        assert holdBackQueue.peek() != null;
        if (nextSeqNum == holdBackQueue.peek().getSequenceNumber()) {
          handleRequest(Objects.requireNonNull(holdBackQueue.poll()));
          this.nextSeqNum++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Request receive() {
    byte[] buf = new byte[32767];
    try {
      DatagramPacket udpPacket = new DatagramPacket(buf, buf.length);
      multicastSocket.receive(udpPacket);
      System.out.println("UDP packet received");
      byte[] responsePayload = udpPacket.getData();
      ObjectInputStream objectInputStream =
          new ObjectInputStream(new ByteArrayInputStream(responsePayload));
      return (Request) objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Call the service implementation in this method
   * @param request the request object
   */
  public abstract void handleRequest(Request request);
}
