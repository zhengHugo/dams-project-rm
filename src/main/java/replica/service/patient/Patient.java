
package replica.service.patient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import replica.dto.AppointmentType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Patient", targetNamespace = "http://endpoint/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Patient {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLocalAppointmentSchedule", targetNamespace = "http://endpoint/", className = "service.patient.GetLocalAppointmentSchedule")
    @ResponseWrapper(localName = "getLocalAppointmentScheduleResponse", targetNamespace = "http://endpoint/", className = "service.patient.GetLocalAppointmentScheduleResponse")
    @Action(input = "http://endpoint/Patient/getLocalAppointmentScheduleRequest", output = "http://endpoint/Patient/getLocalAppointmentScheduleResponse")
    public String getLocalAppointmentSchedule(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "bookAppointment", targetNamespace = "http://endpoint/", className = "service.patient.BookAppointment")
    @ResponseWrapper(localName = "bookAppointmentResponse", targetNamespace = "http://endpoint/", className = "service.patient.BookAppointmentResponse")
    @Action(input = "http://endpoint/Patient/bookAppointmentRequest", output = "http://endpoint/Patient/bookAppointmentResponse")
    public boolean bookAppointment(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
            AppointmentType arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "bookLocalAppointment", targetNamespace = "http://endpoint/", className = "service.patient.BookLocalAppointment")
    @ResponseWrapper(localName = "bookLocalAppointmentResponse", targetNamespace = "http://endpoint/", className = "service.patient.BookLocalAppointmentResponse")
    @Action(input = "http://endpoint/Patient/bookLocalAppointmentRequest", output = "http://endpoint/Patient/bookLocalAppointmentResponse")
    public boolean bookLocalAppointment(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        AppointmentType arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAppointmentSchedule", targetNamespace = "http://endpoint/", className = "service.patient.GetAppointmentSchedule")
    @ResponseWrapper(localName = "getAppointmentScheduleResponse", targetNamespace = "http://endpoint/", className = "service.patient.GetAppointmentScheduleResponse")
    @Action(input = "http://endpoint/Patient/getAppointmentScheduleRequest", output = "http://endpoint/Patient/getAppointmentScheduleResponse")
    public String getAppointmentSchedule(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelAppointment", targetNamespace = "http://endpoint/", className = "service.patient.CancelAppointment")
    @ResponseWrapper(localName = "cancelAppointmentResponse", targetNamespace = "http://endpoint/", className = "service.patient.CancelAppointmentResponse")
    @Action(input = "http://endpoint/Patient/cancelAppointmentRequest", output = "http://endpoint/Patient/cancelAppointmentResponse")
    public boolean cancelAppointment(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        AppointmentType arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelLocalAppointment", targetNamespace = "http://endpoint/", className = "service.patient.CancelLocalAppointment")
    @ResponseWrapper(localName = "cancelLocalAppointmentResponse", targetNamespace = "http://endpoint/", className = "service.patient.CancelLocalAppointmentResponse")
    @Action(input = "http://endpoint/Patient/cancelLocalAppointmentRequest", output = "http://endpoint/Patient/cancelLocalAppointmentResponse")
    public boolean cancelLocalAppointment(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        AppointmentType arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg4
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "swapAppointment", targetNamespace = "http://endpoint/", className = "service.patient.SwapAppointment")
    @ResponseWrapper(localName = "swapAppointmentResponse", targetNamespace = "http://endpoint/", className = "service.patient.SwapAppointmentResponse")
    @Action(input = "http://endpoint/Patient/swapAppointmentRequest", output = "http://endpoint/Patient/swapAppointmentResponse")
    public boolean swapAppointment(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
            AppointmentType arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        AppointmentType arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4);

}
