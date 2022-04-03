package replica.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for appointmentType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="appointmentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Physician"/>
 *     &lt;enumeration value="Surgeon"/>
 *     &lt;enumeration value="Dental"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "appointmentType")
@XmlEnum
public enum AppointmentType {

  @XmlEnumValue("Physician")
  PHYSICIAN("Physician"),
  @XmlEnumValue("Surgeon")
  SURGEON("Surgeon"),
  @XmlEnumValue("Dental")
  DENTAL("Dental");
  private final String value;

  AppointmentType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static AppointmentType fromValue(String v) {
    for (AppointmentType c : AppointmentType.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
