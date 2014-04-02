package org.dieschnittstelle.jee.esa.ejbs.client;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.CUSTOMER_1;
import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.CUSTOMER_2;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;

public class CreateCustomers {

	protected static Logger logger = Logger.getLogger(CreateCustomers.class);

	public static void main(String[] args) {

		try {

			// obtain the beans using a jndi context
			Context context = new InitialContext();
			CustomerCRUDRemote customerCRUD = (CustomerCRUDRemote) context
					.lookup(Constants.CUSTOMER_CRUD_BEAN);
			logger.debug("got customerCRUD: " + customerCRUD);

			if (args.length == 0) {

				// create a customer (we need to pass the changes to the
				// constants in order to be able to use those in other
				// subsequent use cases)
				CUSTOMER_1 = customerCRUD.createCustomer(CUSTOMER_1);
				logger.debug("created customer: " + CUSTOMER_1);

				Util.step();

				// modify the customer changing the address
				Address newAddress = new Address("Gundelfinger Strasse", "36",
						"10318", "Berlin");
				CUSTOMER_1.setAddress(newAddress);
				CUSTOMER_1 = customerCRUD.updateCustomer(CUSTOMER_1);
				logger.debug("updated customer: " + CUSTOMER_1);

				Util.step();

				// read out the customer given the id
				Customer cust = customerCRUD.readCustomer(CUSTOMER_1.getId());
				logger.debug("read customer: " + cust);

				Util.step();

				// delete the customer (to show that this works)
				customerCRUD.deleteCustomer(cust.getId());
				logger.debug("deleted customer");

				Util.step();

				System.err.println("CreateCustomers: done.\n");

			} else {
				// now create the two customers for the allover use case
				CUSTOMER_1 = customerCRUD.createCustomer(CUSTOMER_1);
				logger.debug("created customer1: " + CUSTOMER_1);

				CUSTOMER_2 = customerCRUD.createCustomer(CUSTOMER_2);
				logger.debug("created customer2: " + CUSTOMER_2);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
