package client;

import java.io.Serializable;
import replica.model.City;

public interface ClientId extends Serializable {
  String getId();

  City getCity();
}
