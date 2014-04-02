package org.dieschnittstelle.jee.esa.ejbs.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.entities.CampaignExecution;

public class CreateCampaigns {

	protected static Logger logger = Logger.getLogger(CreateCampaigns.class);

	public static void main(String[] args) {

		try {
			// obtain the beans using a jndi context
			Context context = new InitialContext();
			CampaignTrackingRemote campaignTracking = (CampaignTrackingRemote) context
					.lookup(Constants.CAMPAIGN_TRACKING_BEAN);
			logger.info("got campaign tracking bean: " + campaignTracking);

			// add some campaigns
			campaignTracking.addCampaignExecution(new CampaignExecution(
					Constants.TOUCHPOINT_1, Constants.CAMPAIGN_1.getId(), 10,
					-1));
			campaignTracking
					.addCampaignExecution(new CampaignExecution(
							Constants.TOUCHPOINT_1, Constants.CAMPAIGN_2
									.getId(), 5, -1));

			if (args.length == 0) {
				System.err.println("CreateCampaigns: done.\n");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
