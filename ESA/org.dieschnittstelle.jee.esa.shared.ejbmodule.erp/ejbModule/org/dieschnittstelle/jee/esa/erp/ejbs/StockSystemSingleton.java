package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

import static org.dieschnittstelle.jee.esa.shared.lib.Util.*;

@Singleton
public class StockSystemSingleton implements StockSystemRemote, StockSystemLocal {
	
	protected static Logger logger = Logger
			.getLogger(StockSystemSingleton.class);
	
	@EJB
	private StockItemCRUDLocal stockCRUD;
	
	@EJB
	private PointOfSaleCRUDLocal posCRUD;
	
	
	@Override
	public void addToStock(IndividualisedProductItem product,
			int pointOfSaleId, int units) {
		show("EJB StockSystemStateless,addToStock(), product: " + product +  "pointOfSaleID: "+ pointOfSaleId);
		
		
		// TODO: JPA storing/access
		PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
		
		StockItem item = stockCRUD.readStockItem(product, pos);
		
		if(item == null) {	
			item = new StockItem(product, pos, units);
			stockCRUD.createStockItem(item);	
		} else {
			item.setUnits(item.getUnits()+ units);
			stockCRUD.updateStockItem(item);
		}	
	}

	@Override
	//@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void removeFromStock(IndividualisedProductItem product,
			int pointOfSaleId, int units) {
		StockItem item = stockCRUD.readStockItem(product, posCRUD.readPointOfSale(pointOfSaleId));
		int stockAmount = item.getUnits();
		if (stockAmount <= 0 || (stockAmount - units) < 0) {
			System.err.println("removeFromStock(), amount of stock would be below zero, stock: "+stockAmount);
			return;
		}
		int newStock = stockAmount - units;
		item.setUnits(newStock);
		stockCRUD.updateStockItem(item);
	}
	
	

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId) {
		List <StockItem> stockList = stockCRUD.readStockItemForPointOfSale(posCRUD.readPointOfSale(pointOfSaleId));
		List<IndividualisedProductItem> productList = new ArrayList<IndividualisedProductItem>();
		for (StockItem item: stockList) {
			productList.add(item.getProduct());
		}
		return productList;
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		//for exercise JSF
		return null;
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product,
			int pointOfSaleId) {
		StockItem item = stockCRUD.readStockItem(product, posCRUD.readPointOfSale(pointOfSaleId));
		return item.getUnits();
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		int totalUnits = 0;
		List<StockItem> stockList = stockCRUD.readStockItemsForProduct(product);
		for (StockItem item: stockList) {
			totalUnits += item.getUnits();
		}
		return totalUnits;
	}

	@Override
	public List<Integer> getPointsOfSale(IndividualisedProductItem product) {
		// ArrayList to hold all IDs of point of sale pos
		List<Integer> posIDList = new ArrayList<Integer>();
		
		// read all pos 
		List<PointOfSale> posList = posCRUD.readAllPointsOfSale();

		// iterate over all pos to read all available stock items
		for (PointOfSale pos: posList) {
			List <StockItem> stockItems = stockCRUD.readStockItemForPointOfSale(pos);
			
			for (StockItem stockItem: stockItems) {
				if(stockItem.getProduct().getId() == product.getId()) {
					show("StockSystemSingleton, getPointsOfSale(), found product: "+product);
					
					// product found add the ID of the pos to the list of posIDs
					posIDList.add(stockItem.getPos().getId());
				}
			}
		}
		return posIDList;
	}

	@Override
	public List<StockItem> getCompleteStock() {
		return stockCRUD.getAllStockItems();
	}

	@Override
	public void setPriceForProductOnStock(AbstractProduct prod,
			PointOfSale pos, int price) {
		stockCRUD.setPriceForStockItem(prod, pos, price);	
	}
	
	
}
