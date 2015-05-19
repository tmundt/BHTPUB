package esa.uebungen.jws4;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
//import org.dieschnittstelle.jee.esa.jaxws.TouchpointCRUDWebServiceSOAP;
import org.jboss.logging.Logger;

/*
 * UE JWS4: machen Sie die Funktionalitaet dieser Klasse als Web Service verfuegbar und verwenden Sie fuer 
 * die Umetzung der beiden Methoden die Instanz von GenericCRUDExecutor<AbstractProduct>, 
 * die Sie aus dem ServletContext auslesen koennen
 */
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/uebungen/jws", serviceName = "ProductCRUDWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ProductCRUDWebService {
	
	protected static Logger logger = Logger
			.getLogger(ProductCRUDWebService.class);

	@Resource
	private WebServiceContext wscontext;

	public ProductCRUDWebService() {
		logger.info("<constructor>");
	}
	
	@PostConstruct
	@WebMethod(exclude = true)
	public void initialiseContext() {
		logger.info("@PostConstruct: the wscontext is: " + wscontext);
	}
	
	@WebMethod
	public List<AbstractProduct> readAllProducts() {
		// obtain the CRUD executor from the servlet context
		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) wscontext
				.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
				.getAttribute("productCRUD");
		return productCRUD.readAllObjects();
	}
	
	@WebMethod
	public AbstractProduct createProduct(AbstractProduct product) {
		// obtain the CRUD executor from the servlet context
		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) wscontext
				.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
				.getAttribute("productCRUD");
		return productCRUD.createObject(product);
	}	
	
	@WebMethod
	public AbstractProduct updateProduct(AbstractProduct product) {
		// obtain the CRUD executor from the servlet context
		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) wscontext
				.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
				.getAttribute("productCRUD");
		return productCRUD.updateObject(product);
	}
	
	@WebMethod
	public boolean deleteProduct(int productId) {
		// obtain the CRUD executor from the servlet context
				GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) wscontext
						.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
						.getAttribute("productCRUD");
				return productCRUD.deleteObject(productId);
	}
}
