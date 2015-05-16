package org.dieschnittstelle.jee.esa.jaxrs;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resteasy/touchpoints")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public interface ITouchpointCRUDWebService {
	
	@GET
	public List<StationaryTouchpoint> readAllTouchpoints();
	
	@GET
	@Path("/{id}")
	public StationaryTouchpoint readTouchpoint(@PathParam("id") int id);
	
	@POST
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint); 
	
	@DELETE
	@Path("/{touchpointId}")
	public boolean deleteTouchpoint(@PathParam("touchpointId") int id); 
		
	/*
	 * UE JRS1: add a new annotated method for using the updateTouchpoint functionality of TouchpointCRUDExecutor and implement it
	 */
	@PUT
	public StationaryTouchpoint updateTouchpoint (StationaryTouchpoint touchpoint); 
}
