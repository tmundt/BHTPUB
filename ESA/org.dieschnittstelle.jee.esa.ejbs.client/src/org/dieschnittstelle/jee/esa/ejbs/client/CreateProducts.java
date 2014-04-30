package org.dieschnittstelle.jee.esa.ejbs.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class CreateProducts {
	
	protected static Logger logger = Logger.getLogger(CreateProducts.class);
	
	public static void main(String[] args) {

		try {

			// obtain the beans using a jndi context
			Context context = new InitialContext();
			
			// UE JPA3: lookup eines Remote Interface fuer CRUD bezueglich AbstractProduct
			
			// server-seitige Erzeugung von PRODUCT_1/2 und CAMPAIGN_1/2 durch Aufruf der Methoden des Remote Interface und Uebertragung der durch den Server vergebenen ids auf den Rueckgabewerten in die Werte der Konstanten
			// Constants.PRODUCT_1.setId(productCRUD.createProduct(Constants.PRODUCT_1).getId());
			// Constants.PRODUCT_2.setId(...);
			// Constants.CAMPAIGN_1.setId(...);
			// Constants.CAMPAIGN_2.setId(...);
			
			// Loeschen von CAMPAIGN_2
			
			// Aendern des Namens von PRODUCT_1 auf "Schusterjunge"
			
			// Auslesen aller Produkte
			
			if (args.length == 0) {
				System.err.println("CreateProducts: done.\n");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}


}
