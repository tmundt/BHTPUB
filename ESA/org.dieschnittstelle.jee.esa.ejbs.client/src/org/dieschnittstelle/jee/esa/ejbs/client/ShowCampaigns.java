package org.dieschnittstelle.jee.esa.ejbs.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;

public class ShowCampaigns {

	protected static Logger logger = Logger.getLogger(ShowCampaigns.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			// obtain the beans using a jndi context
			Context context = new InitialContext();
			CampaignTrackingRemote campaignTracking = (CampaignTrackingRemote) context
					.lookup(Constants.CAMPAIGN_TRACKING_BEAN);
			logger.info("campaigns are: " + campaignTracking.getAllCampaignExecutions());
			
			if (args.length == 0) {
				System.err.println("ShowCampaigns: done.\n");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


}
