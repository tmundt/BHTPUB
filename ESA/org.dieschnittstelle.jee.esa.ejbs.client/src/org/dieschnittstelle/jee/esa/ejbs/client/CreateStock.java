package org.dieschnittstelle.jee.esa.ejbs.client;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystem;

public class CreateStock {

	protected static Logger logger = Logger.getLogger(CreateStock.class);

	public static void main(String[] args) {

		// UE EJB2: greifen Sie wie in den anderen Anwendungen auf die von Ihnen
		// erstellte StockSystem EJB zu
		StockSystem stockSystem = null;
		
		// check whether the pointsofsale / touchpoints must be created first
		if (Constants.TOUCHPOINT_1.getErpPointOfSaleId() == -1) {
			CreateTouchpoints.main(new String[]{""});
		}
		else {
			logger.info("no need to create touchpoints.");
		}

		if (stockSystem != null) {

			// fuegen Sie die Produkte hinzu
			stockSystem.addToStock(Constants.PRODUCT_1,
					Constants.TOUCHPOINT_1.getErpPointOfSaleId(), 100);
			stockSystem.addToStock(Constants.PRODUCT_2,
					Constants.TOUCHPOINT_1.getErpPointOfSaleId(), 100);
			stockSystem.addToStock(Constants.PRODUCT_1,
					Constants.TOUCHPOINT_2.getErpPointOfSaleId(), 100);
			stockSystem.addToStock(Constants.PRODUCT_2,
					Constants.TOUCHPOINT_2.getErpPointOfSaleId(), 100);

			// und lassen Sie sich die Vorraete ausgeben
			int s1 = stockSystem.getUnitsOnStock(Constants.PRODUCT_1,
					Constants.TOUCHPOINT_1.getErpPointOfSaleId());
			int s2 = stockSystem.getUnitsOnStock(Constants.PRODUCT_1,
					Constants.TOUCHPOINT_2.getErpPointOfSaleId());
			int s3 = stockSystem.getUnitsOnStock(Constants.PRODUCT_2,
					Constants.TOUCHPOINT_1.getErpPointOfSaleId());
			int s4 = stockSystem.getUnitsOnStock(Constants.PRODUCT_2,
					Constants.TOUCHPOINT_2.getErpPointOfSaleId());

			logger.info("Vorraete: " + s1 + "/" + s2 + ", " + s3 + "/" + s4);

		}
		
		if (args.length == 0) {
			System.err.println("CreateStock: done.\n");
		}
	}

}
