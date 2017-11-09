
package XQBHClient.CommonTran;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the XQBHClient.CommonTran package. 
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

    private final static QName _ComtranResponse_QNAME = new QName("http://ServerTran.XQBHServer/", "ComtranResponse");
    private final static QName _Comtran_QNAME = new QName("http://ServerTran.XQBHServer/", "Comtran");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: XQBHClient.CommonTran
     * 
     */
    public ObjectFactory() {
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ComtranResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServerTran.XQBHServer/", name = "ComtranResponse")
    public JAXBElement<ComtranResponse> createComtranResponse(ComtranResponse value) {
        return new JAXBElement<ComtranResponse>(_ComtranResponse_QNAME, ComtranResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Comtran }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServerTran.XQBHServer/", name = "Comtran")
    public JAXBElement<Comtran> createComtran(Comtran value) {
        return new JAXBElement<Comtran>(_Comtran_QNAME, Comtran.class, null, value);
    }

}
