package org.dieschnittstelle.jee.esa.jaxrs;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.servlets.TouchpointCRUDExecutor;

public class TouchpointCRUDWebServiceImpl implements ITouchpointCRUDWebService {
	
	protected static Logger logger = Logger.getLogger(TouchpointCRUDWebServiceImpl.class);
	
	/**
	 * this accessor will be provided by the ServletContext, to which it is written by the TouchpointServletContextListener
	 */
	private TouchpointCRUDExecutor touchpointCRUD;
	
	/**
	 * here we will be passed the context parameters by the resteasy framework
	 * note that the request context is only declared for illustration purposes, but will not be further used here
	 * @param servletContext
	 */	
	public TouchpointCRUDWebServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		// read out the dataAccessor
		this.touchpointCRUD = (TouchpointCRUDExecutor)servletContext.getAttribute("touchpointCRUD");
		
		logger.debug("read out the touchpointCRUD from the servlet context: " + this.touchpointCRUD);		
	}
	

	@Override
	public List<StationaryTouchpoint> readAllTouchpoints() {
		return (List)this.touchpointCRUD.readAllTouchpoints();
	}

	@Override
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint) {
		return (StationaryTouchpoint)this.touchpointCRUD.createTouchpoint(touchpoint);	
	}

	@Override
	public boolean deleteTouchpoint(int id) {
		return this.touchpointCRUD.deleteTouchpoint(id);	
	}

	/*
	 * UE JRS1: implement the method for updating touchpoints
	 */

}
