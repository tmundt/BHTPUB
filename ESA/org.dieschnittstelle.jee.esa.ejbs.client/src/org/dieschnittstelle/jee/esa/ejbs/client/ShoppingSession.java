package org.dieschnittstelle.jee.esa.ejbs.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;

public class ShoppingSession {

	protected static Logger logger = Logger.getLogger(ShoppingSession.class);

	/*
	 * the three beans that are used
	 */
	private ShoppingCartRemote shoppingCart;

	private CustomerTrackingRemote customerTracking;

	private CampaignTrackingRemote campaignTracking;

	/**
	 * the customer
	 */
	private Customer customer;

	/**
	 * the touchpoint
	 */
	private AbstractTouchpoint touchpoint;

	public ShoppingSession() {
		logger.info("<constructor>");
	}

	/**
	 * access the beans that we need
	 */
	public void initialise() {
		try {
			// obtain the beans using a jndi context
			Context context = new InitialContext();
			this.campaignTracking = (CampaignTrackingRemote) context
					.lookup(Constants.CAMPAIGN_TRACKING_BEAN);
			logger.info("got campaign tracking bean: " + campaignTracking);
			this.customerTracking = (CustomerTrackingRemote) context
					.lookup(Constants.CUSTOMER_TRACKING_BEAN);
			logger.info("got customer tracking bean: " + customerTracking);
			this.shoppingCart = (ShoppingCartRemote) context
					.lookup(Constants.SHOPPING_CART_BEAN);
			logger.info("got shopping cart bean: " + shoppingCart);

		} catch (Exception e) {
			throw new RuntimeException("initialise() failed: " + e, e);
		}
	}

	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void addProduct(AbstractProduct product, int units) {
		this.shoppingCart.addProductBundle(new CrmProductBundle(product.getId(),
				units,product instanceof Campaign));
	}

	/*
	 * verify whether campaigns are still valid
	 */
	public void verifyCampaigns() {
		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot verify campaigns! No touchpoint has been set!");
		}

		for (CrmProductBundle productBundle : this.shoppingCart
				.getProductBundles()) {
			if (productBundle.isCampaign()) {
				// we check whether we have sufficient campaign items available
				if (this.campaignTracking
						.existsValidCampaignExecutionAtTouchpoint(
								productBundle.getErpProductId(),
								this.touchpoint) < productBundle.getUnits()) {
					throw new RuntimeException(
							"verifyCampaigns() failed for productBundle "
									+ productBundle + " at touchpoint "
									+ this.touchpoint + "!");
				}
			}
		}
	}

	public void purchase() {
		logger.info("purchase()");

		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot commit shopping session! Either customer or touchpoint has not been set: "
							+ this.customer + "/" + this.touchpoint);
		}

		// verify the campaigns
		verifyCampaigns();

		// read out the products from the cart
		List<CrmProductBundle> products = this.shoppingCart.getProductBundles();

		// iterate over the products and purchase the campaigns
		for (CrmProductBundle productBundle : this.shoppingCart
				.getProductBundles()) {
			if (productBundle.isCampaign()) {
				this.campaignTracking.purchaseCampaignAtTouchpoint(
						productBundle.getErpProductId(), this.touchpoint,
						productBundle.getUnits());
			}
		}

		// then we add a new customer transaction for the current purchase
		CustomerTransaction transaction = new CustomerTransaction(
				this.customer, this.touchpoint, products);
		transaction.setCompleted(true);
		customerTracking.createTransaction(transaction);

		logger.info("purchase(): done.\n");
	}

}
