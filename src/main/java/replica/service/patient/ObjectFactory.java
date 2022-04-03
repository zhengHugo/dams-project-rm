
package replica.service.patient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service.patient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CancelAppointmentResponse_QNAME = new QName("http://endpoint/", "cancelAppointmentResponse");
    private final static QName _BookLocalAppointment_QNAME = new QName("http://endpoint/", "bookLocalAppointment");
    private final static QName _GetAppointmentSchedule_QNAME = new QName("http://endpoint/", "getAppointmentSchedule");
    private final static QName _GetAppointmentScheduleResponse_QNAME = new QName("http://endpoint/", "getAppointmentScheduleResponse");
    private final static QName _BookAppointmentResponse_QNAME = new QName("http://endpoint/", "bookAppointmentResponse");
    private final static QName _CancelLocalAppointment_QNAME = new QName("http://endpoint/", "cancelLocalAppointment");
    private final static QName _BookLocalAppointmentResponse_QNAME = new QName("http://endpoint/", "bookLocalAppointmentResponse");
    private final static QName _BookAppointment_QNAME = new QName("http://endpoint/", "bookAppointment");
    private final static QName _GetLocalAppointmentSchedule_QNAME = new QName("http://endpoint/", "getLocalAppointmentSchedule");
    private final static QName _SwapAppointmentResponse_QNAME = new QName("http://endpoint/", "swapAppointmentResponse");
    private final static QName _SwapAppointment_QNAME = new QName("http://endpoint/", "swapAppointment");
    private final static QName _CancelAppointment_QNAME = new QName("http://endpoint/", "cancelAppointment");
    private final static QName _CancelLocalAppointmentResponse_QNAME = new QName("http://endpoint/", "cancelLocalAppointmentResponse");
    private final static QName _GetLocalAppointmentScheduleResponse_QNAME = new QName("http://endpoint/", "getLocalAppointmentScheduleResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service.patient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelLocalAppointmentResponse }
     * 
     */
    public CancelLocalAppointmentResponse createCancelLocalAppointmentResponse() {
        return new CancelLocalAppointmentResponse();
    }

    /**
     * Create an instance of {@link CancelAppointment }
     * 
     */
    public CancelAppointment createCancelAppointment() {
        return new CancelAppointment();
    }

    /**
     * Create an instance of {@link GetLocalAppointmentScheduleResponse }
     * 
     */
    public GetLocalAppointmentScheduleResponse createGetLocalAppointmentScheduleResponse() {
        return new GetLocalAppointmentScheduleResponse();
    }

    /**
     * Create an instance of {@link GetLocalAppointmentSchedule }
     * 
     */
    public GetLocalAppointmentSchedule createGetLocalAppointmentSchedule() {
        return new GetLocalAppointmentSchedule();
    }

    /**
     * Create an instance of {@link SwapAppointmentResponse }
     * 
     */
    public SwapAppointmentResponse createSwapAppointmentResponse() {
        return new SwapAppointmentResponse();
    }

    /**
     * Create an instance of {@link SwapAppointment }
     * 
     */
    public SwapAppointment createSwapAppointment() {
        return new SwapAppointment();
    }

    /**
     * Create an instance of {@link BookAppointment }
     * 
     */
    public BookAppointment createBookAppointment() {
        return new BookAppointment();
    }

    /**
     * Create an instance of {@link BookLocalAppointmentResponse }
     * 
     */
    public BookLocalAppointmentResponse createBookLocalAppointmentResponse() {
        return new BookLocalAppointmentResponse();
    }

    /**
     * Create an instance of {@link CancelLocalAppointment }
     * 
     */
    public CancelLocalAppointment createCancelLocalAppointment() {
        return new CancelLocalAppointment();
    }

    /**
     * Create an instance of {@link BookAppointmentResponse }
     * 
     */
    public BookAppointmentResponse createBookAppointmentResponse() {
        return new BookAppointmentResponse();
    }

    /**
     * Create an instance of {@link GetAppointmentScheduleResponse }
     * 
     */
    public GetAppointmentScheduleResponse createGetAppointmentScheduleResponse() {
        return new GetAppointmentScheduleResponse();
    }

    /**
     * Create an instance of {@link BookLocalAppointment }
     * 
     */
    public BookLocalAppointment createBookLocalAppointment() {
        return new BookLocalAppointment();
    }

    /**
     * Create an instance of {@link GetAppointmentSchedule }
     * 
     */
    public GetAppointmentSchedule createGetAppointmentSchedule() {
        return new GetAppointmentSchedule();
    }

    /**
     * Create an instance of {@link CancelAppointmentResponse }
     * 
     */
    public CancelAppointmentResponse createCancelAppointmentResponse() {
        return new CancelAppointmentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "cancelAppointmentResponse")
    public JAXBElement<CancelAppointmentResponse> createCancelAppointmentResponse(CancelAppointmentResponse value) {
        return new JAXBElement<CancelAppointmentResponse>(_CancelAppointmentResponse_QNAME, CancelAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookLocalAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "bookLocalAppointment")
    public JAXBElement<BookLocalAppointment> createBookLocalAppointment(BookLocalAppointment value) {
        return new JAXBElement<BookLocalAppointment>(_BookLocalAppointment_QNAME, BookLocalAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppointmentSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "getAppointmentSchedule")
    public JAXBElement<GetAppointmentSchedule> createGetAppointmentSchedule(GetAppointmentSchedule value) {
        return new JAXBElement<GetAppointmentSchedule>(_GetAppointmentSchedule_QNAME, GetAppointmentSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppointmentScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "getAppointmentScheduleResponse")
    public JAXBElement<GetAppointmentScheduleResponse> createGetAppointmentScheduleResponse(GetAppointmentScheduleResponse value) {
        return new JAXBElement<GetAppointmentScheduleResponse>(_GetAppointmentScheduleResponse_QNAME, GetAppointmentScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "bookAppointmentResponse")
    public JAXBElement<BookAppointmentResponse> createBookAppointmentResponse(BookAppointmentResponse value) {
        return new JAXBElement<BookAppointmentResponse>(_BookAppointmentResponse_QNAME, BookAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelLocalAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "cancelLocalAppointment")
    public JAXBElement<CancelLocalAppointment> createCancelLocalAppointment(CancelLocalAppointment value) {
        return new JAXBElement<CancelLocalAppointment>(_CancelLocalAppointment_QNAME, CancelLocalAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookLocalAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "bookLocalAppointmentResponse")
    public JAXBElement<BookLocalAppointmentResponse> createBookLocalAppointmentResponse(BookLocalAppointmentResponse value) {
        return new JAXBElement<BookLocalAppointmentResponse>(_BookLocalAppointmentResponse_QNAME, BookLocalAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "bookAppointment")
    public JAXBElement<BookAppointment> createBookAppointment(BookAppointment value) {
        return new JAXBElement<BookAppointment>(_BookAppointment_QNAME, BookAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLocalAppointmentSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "getLocalAppointmentSchedule")
    public JAXBElement<GetLocalAppointmentSchedule> createGetLocalAppointmentSchedule(GetLocalAppointmentSchedule value) {
        return new JAXBElement<GetLocalAppointmentSchedule>(_GetLocalAppointmentSchedule_QNAME, GetLocalAppointmentSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SwapAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "swapAppointmentResponse")
    public JAXBElement<SwapAppointmentResponse> createSwapAppointmentResponse(SwapAppointmentResponse value) {
        return new JAXBElement<SwapAppointmentResponse>(_SwapAppointmentResponse_QNAME, SwapAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SwapAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "swapAppointment")
    public JAXBElement<SwapAppointment> createSwapAppointment(SwapAppointment value) {
        return new JAXBElement<SwapAppointment>(_SwapAppointment_QNAME, SwapAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "cancelAppointment")
    public JAXBElement<CancelAppointment> createCancelAppointment(CancelAppointment value) {
        return new JAXBElement<CancelAppointment>(_CancelAppointment_QNAME, CancelAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelLocalAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "cancelLocalAppointmentResponse")
    public JAXBElement<CancelLocalAppointmentResponse> createCancelLocalAppointmentResponse(CancelLocalAppointmentResponse value) {
        return new JAXBElement<CancelLocalAppointmentResponse>(_CancelLocalAppointmentResponse_QNAME, CancelLocalAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLocalAppointmentScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint/", name = "getLocalAppointmentScheduleResponse")
    public JAXBElement<GetLocalAppointmentScheduleResponse> createGetLocalAppointmentScheduleResponse(GetLocalAppointmentScheduleResponse value) {
        return new JAXBElement<GetLocalAppointmentScheduleResponse>(_GetLocalAppointmentScheduleResponse_QNAME, GetLocalAppointmentScheduleResponse.class, null, value);
    }

}
