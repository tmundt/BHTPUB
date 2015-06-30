package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.jboss.logging.Logger;

@Stateless
public class ProductCRUDStateless implements ProductCRUDRemote {
	
	protected static Logger logger = Logger
			.getLogger(ProductCRUDStateless.class);
	
	@PersistenceContext(unitName="crm_erp_PU")
	private EntityManager em;

	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		em.persist(prod);
		System.out.println("ProductCRUDStateless(), createProduct, prod:" + prod);
		return prod;
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		logger.info("readAllProducts()");

		Query query = em.createQuery("FROM AbstractProduct");

		List<AbstractProduct> productList = (List<AbstractProduct>) query
				.getResultList();

		logger.info("readAllTouchpoints(): " + productList);
		return productList;
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		logger.info("updateTouchpoint(): before merge(): " + update);
		update = em.merge(update);

		logger.info("updateTouchpoint(): after merge(): " + update);
		return update;
	}

	@Override
	public AbstractProduct readProduct(int productID) {
		//logger.info("readProduct() in ProductCRUDStatless, productId: " + productID);
		AbstractProduct product = em.find(AbstractProduct.class, productID);
		return product;
	}

	@Override
	public boolean deleteProduct(int productID) {
		logger.info("deleteTouchpoint(): " + productID);

		em.remove(em.find(AbstractProduct.class, productID));

		logger.info("deleteProduct(): done");

		return true;
	}
}
