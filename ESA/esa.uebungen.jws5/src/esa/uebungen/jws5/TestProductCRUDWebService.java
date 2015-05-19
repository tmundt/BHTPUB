package esa.uebungen.jws5;

import java.util.List;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.uebungen.jws.ProductCRUDWebService;
import org.dieschnittstelle.jee.esa.uebungen.jws.ProductCRUDWebService_Service;
import org.dieschnittstelle.jee.esa.uebungen.jws.ProductType;

/*
 * UE JWS5: rufen Sie hier den in JWS4 implementierten Web Service auf.
 */
public class TestProductCRUDWebService {
	
	public static void main(String[] args) {
		
//		// create two products and add them to the list of products
		IndividualisedProductItem prod1 = new IndividualisedProductItem();
		prod1.setName("Schrippe");
		prod1.setProductType(ProductType.ROLL);
		prod1.setPrice(720);
		
		IndividualisedProductItem prod2 = new IndividualisedProductItem();
		prod2.setName("Kirschplunder");
		prod2.setProductType(ProductType.PASTRY);
		prod2.setPrice(1080);
		
		/*
		 * initialisieren ein Service Interface fuer den in JWS4 erstellten Web Service
		 */		
		ProductCRUDWebService_Service service = new ProductCRUDWebService_Service();
		ProductCRUDWebService serviceOperations = service.getProductCRUDWebServicePort();
		
		/*
		 * rufen Sie die im Interface deklarierte Methode fuer das Erzeugen von Produkten fuer prod1 und prod2 auf und geben Sie die Rueckgabewerte auf der Kosole aus.
		 */
		prod1 = (IndividualisedProductItem) serviceOperations.createProduct(prod1);
		System.out.println("created product: " + prod1.getName());
		prod2 = (IndividualisedProductItem) serviceOperations.createProduct(prod2);
		System.out.println("created product: " + prod2.getName());
		
		/*
		 * rufen Sie die im Interface deklarierte Methode fuer das Auslesen aller Produkte auf und geben Sie den Rueckgabewert auf der Konsole aus.
		 */
		List<AbstractProduct> products = serviceOperations
				.readAllProducts().getItem();
		System.out.println("read all products: " + products);
		
		
	
		System.err.println("TestProductCRUDWebService: done.\n");

	}
	
}
