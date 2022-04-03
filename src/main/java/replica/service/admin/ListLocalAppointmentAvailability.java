
package replica.service.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "listLocalAppointmentAvailability", namespace = "http://endpoint.replica/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listLocalAppointmentAvailability", namespace = "http://endpoint.replica/")
public class ListLocalAppointmentAvailability {

    @XmlElement(name = "arg0", namespace = "")
    private replica.dto.AppointmentType arg0;

    /**
     * 
     * @return
     *     returns AppointmentType
     */
    public replica.dto.AppointmentType getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(replica.dto.AppointmentType arg0) {
        this.arg0 = arg0;
    }

}
