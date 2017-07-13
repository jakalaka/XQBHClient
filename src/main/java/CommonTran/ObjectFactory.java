
package CommonTran;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the CommonTran package. 
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

    private final static QName _ComtranResponse_QNAME = new QName("http://ServerTran/", "ComtranResponse");
    private final static QName _Comtran_QNAME = new QName("http://ServerTran/", "Comtran");
    private final static QName _GetOutResponse_QNAME = new QName("http://ServerTran/", "getOutResponse");
    private final static QName _GetOut_QNAME = new QName("http://ServerTran/", "getOut");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: CommonTran
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TranObj }
     * 
     */
    public TranObj createTranObj() {
        return new TranObj();
    }

    /**
     * Create an instance of {@link TranObj.TranMap }
     * 
     */
    public TranObj.TranMap createTranObjTranMap() {
        return new TranObj.TranMap();
    }

    /**
     * Create an instance of {@link TranObj.HeadMap }
     * 
     */
    public TranObj.HeadMap createTranObjHeadMap() {
        return new TranObj.HeadMap();
    }

    /**
     * Create an instance of {@link GetOut }
     * 
     */
    public GetOut createGetOut() {
        return new GetOut();
    }

    /**
     * Create an instance of {@link ComtranResponse }
     * 
     */
    public ComtranResponse createComtranResponse() {
        return new ComtranResponse();
    }

    /**
     * Create an instance of {@link Comtran }
     * 
     */
    public Comtran createComtran() {
        return new Comtran();
    }

    /**
     * Create an instance of {@link GetOutResponse }
     * 
     */
    public GetOutResponse createGetOutResponse() {
        return new GetOutResponse();
    }

    /**
     * Create an instance of {@link TranObj.TranMap.Entry }
     * 
     */
    public TranObj.TranMap.Entry createTranObjTranMapEntry() {
        return new TranObj.TranMap.Entry();
    }

    /**
     * Create an instance of {@link TranObj.HeadMap.Entry }
     * 
     */
    public TranObj.HeadMap.Entry createTranObjHeadMapEntry() {
        return new TranObj.HeadMap.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComtranResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServerTran/", name = "ComtranResponse")
    public JAXBElement<ComtranResponse> createComtranResponse(ComtranResponse value) {
        return new JAXBElement<ComtranResponse>(_ComtranResponse_QNAME, ComtranResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Comtran }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServerTran/", name = "Comtran")
    public JAXBElement<Comtran> createComtran(Comtran value) {
        return new JAXBElement<Comtran>(_Comtran_QNAME, Comtran.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServerTran/", name = "getOutResponse")
    public JAXBElement<GetOutResponse> createGetOutResponse(GetOutResponse value) {
        return new JAXBElement<GetOutResponse>(_GetOutResponse_QNAME, GetOutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOut }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServerTran/", name = "getOut")
    public JAXBElement<GetOut> createGetOut(GetOut value) {
        return new JAXBElement<GetOut>(_GetOut_QNAME, GetOut.class, null, value);
    }

}
