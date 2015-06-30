package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

@Stateless
public class StockItemCRUDStateless implements StockItemCRUDLocal {
	
	protected static Logger logger = Logger
			.getLogger(ProductCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

	@Override
	public StockItem createStockItem(StockItem item) {
		System.out.println("createStockItem(), item: " + item);
		
		// if product is not within entitymanager
		if(!em.contains(item.getProduct())) {
			item.setProduct(em.merge(item.getProduct())); //mark for em to be added
		}
		em.persist(item); // create item on database
		return item;
	}

	@Override
	public StockItem readStockItem(AbstractProduct prod, PointOfSale pos) {
		StockItem item = em.find(StockItem.class, new ProductAtPosPK((IndividualisedProductItem) prod, pos));
		return item;
	}

	@Override
	public StockItem updateStockItem(StockItem item) {
		logger.info("updateStockItem(): before merge(): " + item);
		item = em.merge(item);

		logger.info("updateStockItem(): after merge(): " + item);
		return item;
	}

	@Override
	public List<StockItem> readStockItemsForProduct(AbstractProduct prod) {
		Query qu = em.createQuery("SELECT si FROM StockItem si WHERE si.product="+ prod.getId());
		return qu.getResultList();
	}

	@Override
	public List<StockItem> readStockItemForPointOfSale(PointOfSale pos) {
		Query qu = em.createQuery("SELECT si FROM StockItem si WHERE si.pos.id="+ pos.getId());
		List<StockItem> stockList = qu.getResultList();
		return stockList;
	}

	@Override
	public List<StockItem> getAllStockItems() {
		return em.createQuery("FROM StockItem").getResultList();
	}

}
