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
			
			// TODO: server-seitige Erzeugung von PRODUCT_1/2 und CAMPAIGN_1/2 durch Aufruf der Methoden des Remote Interface
			// PRODUCT_1 = ...
			// PRODUCT_2 = ...
			// CAMPAIGN_1 = ...
			// CAMPAIGN_2 = ...
			
			if (args.length == 0) {
				System.err.println("CreateProducts: done.\n");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}


}
