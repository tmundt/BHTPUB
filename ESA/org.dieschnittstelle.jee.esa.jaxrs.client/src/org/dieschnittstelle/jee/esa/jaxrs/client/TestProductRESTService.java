package org.dieschnittstelle.jee.esa.jaxrs.client;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.jaxrs.IProductCRUDWebService;
import org.dieschnittstelle.jee.esa.jaxrs.ITouchpointCRUDWebService;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class TestProductRESTService {

	protected static Logger logger = Logger.getLogger(TestProductRESTService.class);
	
	public static void main(String[] args) {
		
		
		/*
		 * register jackson as reader and writer for json
		 */
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());		
		// the MessageBodyReader must be added explicitly to the provider factory. Here, we use the jackson implementation
		ResteasyProviderFactory.getInstance().addMessageBodyReader(JacksonJaxbJsonProvider.class);
		ResteasyProviderFactory.getInstance().addMessageBodyWriter(JacksonJaxbJsonProvider.class);
		
		/*
		 * create a client for the web service passing the interface
		 */
		IProductCRUDWebService serviceClient = ProxyFactory.create(IProductCRUDWebService.class,
				"http://localhost:8888/org.dieschnittstelle.jee.esa.jaxrs",
				new ApacheHttpClient4Executor());
		logger.info("created client: " + serviceClient);
		
		// create two products and add them to the list of products
		IndividualisedProductItem prod1 = new IndividualisedProductItem("Schrippe",	ProductType.ROLL, 720);
		IndividualisedProductItem prod2 = new IndividualisedProductItem("Kirschplunder",ProductType.PASTRY, 1080);
		
		/*
		 * UE JRS2: rufen Sie auf serviceClient die im Interface deklarierte Methode fuer das Erzeugen von Produkten fuer prod1 und prod2 auf
		 */
		
		/*
		 * UE JRS2: rufen Sie auf serviceClient die im Interface deklarierte Methode fuer das Auslesen aller Produkte auf
		 */
				
	}
	
}
