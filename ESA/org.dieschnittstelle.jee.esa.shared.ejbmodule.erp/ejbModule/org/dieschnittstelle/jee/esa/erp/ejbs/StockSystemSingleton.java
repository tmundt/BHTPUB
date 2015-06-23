package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

import static org.dieschnittstelle.jee.esa.shared.lib.Util.*;

@Singleton
public class StockSystemSingleton implements StockSystemRemote {
	
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
		StockItem item = new StockItem(product, pos, units);	
		stockCRUD.createStockItem(item);
	}

	@Override
	//@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void removeFromStock(IndividualisedProductItem product,
			int pointOfSaleId, int units) {
		StockItem item = stockCRUD.readStockItem(product, posCRUD.readPointOfSale(pointOfSaleId));
		int newStock = item.getUnits() - units;
		item.setUnits(newStock);
		stockCRUD.updateStockItem(item);

	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId) {
		List <StockItem> stockList = stockCRUD.readStockItemForPointOfSale(posCRUD.readPointOfSale(pointOfSaleId));
		//stockCRUD.readStockItemsForProduct(stockCRUD.readStockItemForPointOfSale(posCRUD.readPointOfSale(pointOfSaleId)));
		//show("StockSystemSingleton, getProductsOnStock(): " + stockList);
		List<IndividualisedProductItem> productList = new ArrayList<IndividualisedProductItem>();
		for (StockItem item: stockList) {
			productList.add(item.getProduct());
		}
		return productList;
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		//stockCRUD.
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
		//List<IndividualisedProductItem> productList = new ArrayList<IndividualisedProductItem>();
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
//		posIDList.add(1);
		return posIDList;
	}
}
