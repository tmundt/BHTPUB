package org.dieschnittstelle.jee.esa.ejbs.client;

import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerTransactionCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

/*
 * this is not running in standalone mode!
 */
public class ShowTransactions {

	protected static Logger logger = Logger.getLogger(ShowTransactions.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.err.println("ShowTransactions is not running standalone. Use TotalUsecase for calling it.");
			return;
		}

		try {
			// obtain the beans using a jndi context
			Context context = new InitialContext();
			CustomerTransactionCRUDRemote transactionsCRUD = (CustomerTransactionCRUDRemote) context
					.lookup(Constants.TRANSACTIONS_CRUD_BEAN);
			
			// read out transactions for some touchpoint
			Collection<CustomerTransaction> trans = transactionsCRUD.readAllTransactionsForTouchpoint(Constants.TOUCHPOINT_1);			
			logger.info("transactions for touchpoint are: "
					+ trans);
			
			Util.step();
			
			trans = transactionsCRUD.readAllTransactionsForCustomer(Constants.CUSTOMER_1);			
			logger.info("transactions for customer are: "
					+ trans);
			
			Util.step();
			
			trans = transactionsCRUD.readAllTransactionsForTouchpointAndCustomer(Constants.TOUCHPOINT_1,Constants.CUSTOMER_1);			
			logger.info("transactions for touchpoint and customer are: "
					+ trans);
			
			Util.step();
			
			// now try to read out the transactions by obtaining the customer and retrieving getTransactions()
			trans = Constants.CUSTOMER_1.getTransactions();
			logger.info("transactions on local customer object are: " + trans);
			
			Util.step();
			
			CustomerCRUDRemote customerCRUD = (CustomerCRUDRemote) context
					.lookup(Constants.CUSTOMER_CRUD_BEAN);
			Customer cust = customerCRUD.readCustomer(Constants.CUSTOMER_1.getId());
			logger.info("read remote customer object: " + cust);
			
			Util.step();
			
			trans = cust.getTransactions();
			logger.info("read transactions from remote object: " + trans);
						
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
