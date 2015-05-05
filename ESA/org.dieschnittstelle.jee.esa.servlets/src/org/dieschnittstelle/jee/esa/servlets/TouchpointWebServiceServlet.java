package org.dieschnittstelle.jee.esa.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

public class TouchpointWebServiceServlet extends HttpServlet {

	protected static Logger logger = Logger
			.getLogger(TouchpointWebServiceServlet.class);

	public TouchpointWebServiceServlet() {
		System.err.println("TouchpointWebServiceServlet: constructor invoked\n");
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("doGet()");

		// we assume here that GET will only be used to return the list of all
		// touchpoints

		// obtain the executor for reading out the touchpoints
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			// set the status
			response.setStatus(HttpServletResponse.SC_OK);
			// obtain the output stream from the response and write the list of
			// touchpoints into the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			// write the object
			oos.writeObject(exec.readAllTouchpoints());
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}
	
	
	@Override	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("doPost()");

		// assume POST will only be used for touchpoint creation, i.e. there is
		// no need to check the uri that has been used

		// obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		
		try {
			// create an ObjectInputStream from the request's input stream
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream()); 
		
			// read an AbstractTouchpoint object from the stream
			AbstractTouchpoint tp = (AbstractTouchpoint)ois.readObject();
			
			// call the create method on the executor and take its return value
			AbstractTouchpoint tp2 =  exec.createTouchpoint(tp);
		
			// set the response status as successful, using the appropriate
			// constant from HttpServletResponse
			response.setStatus(HttpServletResponse.SC_OK);
		
			// then write the object to the response's output stream, using a
			// wrapping ObjectOutputStream
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		
			// ... and write the object to the stream
			oos.writeObject(tp2);
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("doDelete()");
		// obtain the executor for deleting the touchpoint from the servlet context using the touchpointCRUD attribute
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		
		try {
			
			int touchpointID = Integer.valueOf(request.getRequestURL().toString().split("/")[7]);
			
			System.out.println("WebService delete, id: " + touchpointID);
			
			// call the delete method on the executor and take its return value/boolean
			// to see if the deletion was successfull
			Boolean tpDeleted =  exec.deleteTouchpoint(touchpointID);
			
			// show client the successful deletion
			if(tpDeleted == true) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				// show client that the requested item to be deleted was not found
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
