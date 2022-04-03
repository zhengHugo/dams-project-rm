
package replica.service.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "removeAppointment", namespace = "http://endpoint.replica/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeAppointment", namespace = "http://endpoint.replica/", propOrder = {
    "arg0",
    "arg1"
})
public class RemoveAppointment {

    @XmlElement(name = "arg0", namespace = "")
    private String arg0;
    @XmlElement(name = "arg1", namespace = "")
    private replica.dto.AppointmentType arg1;

    /**
     * 
     * @return
     *     returns String
     */
    public String getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(String arg0) {
        this.arg0 = arg0;
    }

    /**
     * 
     * @return
     *     returns AppointmentType
     */
    public replica.dto.AppointmentType getArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(replica.dto.AppointmentType arg1) {
        this.arg1 = arg1;
    }

}
