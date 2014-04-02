package org.dieschnittstelle.jee.esa.ejbs.client;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.TOUCHPOINT_1;
import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.TOUCHPOINT_2;
import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.TOUCHPOINT_3;
import static org.dieschnittstelle.jee.esa.ejbs.client.Util.step;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;

public class CreateTouchpoints {

	protected static Logger logger = Logger.getLogger(CreateTouchpoints.class);

	public static void main(String[] args) {

		try {

			// obtain the beans using a jndi context
			Context context = new InitialContext();
			TouchpointCRUDRemote touchpointCRUD = (TouchpointCRUDRemote) context
					.lookup(Constants.TOUCHPOINT_CRUD_BEAN);
			logger.debug("got touchpointCRUD: " + touchpointCRUD);
			PointOfSaleCRUDRemote posCRUD = (PointOfSaleCRUDRemote) context
					.lookup(Constants.POS_CRUD_BEAN);

			boolean dostep = (args.length == 0);

			// we first create the erp pos and then a corresponding touchpoint
			PointOfSale pos1 = posCRUD.createPointOfSale(new PointOfSale());
			TOUCHPOINT_1.setErpPointOfSaleId(pos1.getId());
			TOUCHPOINT_1 = (StationaryTouchpoint) touchpointCRUD
					.createTouchpoint(TOUCHPOINT_1);
			logger.debug("created tp1: " + TOUCHPOINT_1);

			if (dostep)
				step();

			PointOfSale pos2 = posCRUD.createPointOfSale(new PointOfSale());
			TOUCHPOINT_2.setErpPointOfSaleId(pos2.getId());
			TOUCHPOINT_2 = (StationaryTouchpoint) touchpointCRUD
					.createTouchpoint(TOUCHPOINT_2);
			logger.debug("created tp2: " + TOUCHPOINT_2);

			if (dostep)
				step();

			PointOfSale pos3 = posCRUD.createPointOfSale(new PointOfSale());
			TOUCHPOINT_3.setErpPointOfSaleId(pos3.getId());
			TOUCHPOINT_3 = (MobileTouchpoint) touchpointCRUD
					.createTouchpoint(TOUCHPOINT_3);
			logger.debug("created tp3: " + TOUCHPOINT_3);

			if (dostep)
				step();

			// read out all touchpoints
			List<AbstractTouchpoint> tps = touchpointCRUD.readAllTouchpoints();
			logger.debug("read all touchpoints: " + tps);

			if (args.length == 0) {
				System.err.println("CreateTouchpoints: done.\n");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
