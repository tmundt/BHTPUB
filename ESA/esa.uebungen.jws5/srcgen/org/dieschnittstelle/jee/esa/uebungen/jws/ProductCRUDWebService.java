package org.dieschnittstelle.jee.esa.uebungen.jws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.13
 * 2015-05-19T19:33:06.589+02:00
 * Generated source version: 2.7.13
 * 
 */
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws", name = "ProductCRUDWebService")
@XmlSeeAlso({org.dieschnittstelle.jee.esa.erp.entities.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ProductCRUDWebService {

    @WebResult(name = "deleteProductResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws", partName = "deleteProductResponse")
    @WebMethod
    public boolean deleteProduct(
        @WebParam(partName = "deleteProduct", name = "deleteProduct", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws")
        int deleteProduct
    );

    @WebResult(name = "readAllProductsResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws", partName = "readAllProductsResponse")
    @WebMethod
    public org.dieschnittstelle.jee.esa.erp.entities.AbstractProductArray readAllProducts();

    @WebResult(name = "createProductResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws", partName = "createProductResponse")
    @WebMethod
    public org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct createProduct(
        @WebParam(partName = "createProduct", name = "createProduct", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws")
        org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct createProduct
    );

    @WebResult(name = "updateProductResponse", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws", partName = "updateProductResponse")
    @WebMethod
    public org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct updateProduct(
        @WebParam(partName = "updateProduct", name = "updateProduct", targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws")
        org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct updateProduct
    );
}