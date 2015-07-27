package org.dieschnittstelle.jee.esa.shopping;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingException;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;

@Stateful
public class ShoppingSessionFacadeStateful implements
		ShoppingSessionFacadeRemote {

	// protected static Logger logger = Logger.getLogger(ShoppingSession.class);

	/*
	 * the three beans that are used
	 */

	@EJB
	private ShoppingCartLocal shoppingCart;

	@EJB
	private CustomerTrackingLocal customerTracking;

	@EJB
	private CampaignTrackingLocal campaignTracking;
	
	@EJB
	private StockSystemLocal stockSystem;

	/**
	 * the customer
	 */
	private Customer customer;

	/**
	 * the touchpoint
	 */
	private AbstractTouchpoint touchpoint;

	@Override
	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}

	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;

	}

	@Override
	public void addProduct(AbstractProduct product, int units) {
		this.shoppingCart.addProductBundle(new CrmProductBundle(
				product.getId(), units, product instanceof Campaign));
	}

	/*
	 * verify whether campaigns are still valid
	 */
	public void verifyCampaigns() {
		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot verify campaigns! No touchpoint has been set!");
		}
		try {
			for (CrmProductBundle productBundle : this.shoppingCart
					.getProductBundles()) {
				if (productBundle.isCampaign()) {
					int availableCampaigns = this.campaignTracking
							.existsValidCampaignExecutionAtTouchpoint(
									productBundle.getErpProductId(),
									this.touchpoint);
					System.out.println("got available campaigns for product "
							+ productBundle.getErpProductId() + ": "
							+ availableCampaigns);
					// we check whether we have sufficient campaign items
					// available
					if (availableCampaigns < productBundle.getUnits()) {
						throw new RuntimeException(
								"verifyCampaigns() failed for productBundle "
										+ productBundle
										+ " at touchpoint "
										+ this.touchpoint
										+ "! Need "
										+ productBundle.getUnits()
										+ " instances of campaign, but only got: "
										+ availableCampaigns);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("verifyCampagins() not possible, got: "
					+ e.getMessage());
			throw new ShoppingException(
					ShoppingException.ShoppingSessionExceptionReason.CAMPAIGN_INVALID);
		}

	}

	public void purchase() {
		System.out.println("purchase()");

		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot commit shopping session! Either customer or touchpoint has not been set: "
							+ this.customer + "/" + this.touchpoint);
		}

		// verify the campaigns
		verifyCampaigns();

		// remove the products from stock
		// checkAndRemoveProductsFromStock();

		// iterate over the products and purchase the campaigns
		List<CrmProductBundle> products = this.shoppingCart.getProductBundles();
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

		System.out.println("purchase(): done.\n");
	}
	/**
	 * Check and remove products from stock
	 * check: check amount of available products on stock
	 * remove: remove the items from the stock
	 */
	@Override
	public void checkAndRemoveProductsFromStock() {
		//this.shoppingCart.getProductBundles()
		//stockSystem.getTotalUnitsOnStock(product)
	}

}
