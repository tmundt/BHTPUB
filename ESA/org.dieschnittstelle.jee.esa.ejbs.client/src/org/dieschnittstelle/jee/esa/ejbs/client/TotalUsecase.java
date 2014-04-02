package org.dieschnittstelle.jee.esa.ejbs.client;

import org.apache.log4j.Logger;

public class TotalUsecase {

	protected static Logger logger = Logger.getLogger(TotalUsecase.class);
	
	public static void main(String[] args) {
		
		CreateProducts.main(new String[]{""});
		logger.debug("created products");
		
		Util.step();
		
		CreateTouchpoints.main(new String[]{""});
		logger.debug("created touchpoints");
		
		Util.step();
		
		CreateStock.main(new String[]{""});
		CreateCampaigns.main(new String[]{""});
		logger.debug("created stocks and campaigns");
		
		Util.step();

		CreateCustomers.main(new String[]{""});
		logger.debug("created customers");
		
		Util.step();

		DoShopping.main(new String[]{""});
		
		Util.step();
		
		ShowTransactions.main(new String[]{""});
		
		System.err.println("TotalUsecase: done.\n");

	}
	
}

