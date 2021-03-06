package org.dieschnittstelle.jee.esa.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.13
 * 2015-05-16T19:47:25.154+02:00
 * Generated source version: 2.7.13
 * 
 */
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", name = "TouchpointCRUDWebServiceSOAP")
@XmlSeeAlso({org.dieschnittstelle.jee.esa.crm.entities.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface TouchpointCRUDWebServiceSOAP {

    @WebResult(name = "readAllTouchpointsResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", partName = "readAllTouchpointsResponse")
    @WebMethod
    public org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpointArray readAllTouchpoints();

    @WebResult(name = "updateTouchpointResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", partName = "updateTouchpointResponse")
    @WebMethod
    public org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint updateTouchpoint(
        @WebParam(partName = "updateTouchpoint", name = "updateTouchpoint", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws")
        org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint updateTouchpoint
    );

    @WebResult(name = "createTouchpointResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", partName = "createTouchpointResponse")
    @WebMethod
    public org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint createTouchpoint(
        @WebParam(partName = "createTouchpoint", name = "createTouchpoint", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws")
        org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint createTouchpoint
    );

    @WebResult(name = "deleteTouchpointResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", partName = "deleteTouchpointResponse")
    @WebMethod
    public boolean deleteTouchpoint(
        @WebParam(partName = "deleteTouchpoint", name = "deleteTouchpoint", targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws")
        int deleteTouchpoint
    );
}
