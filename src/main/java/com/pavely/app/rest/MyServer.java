package com.pavely.app.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pavely.app.db.Line;
import com.pavely.app.db.LinesDAO;
import com.pavely.app.db.LinesUserInputFilter;

@Path("/data")
@Component
public class MyServer {
	 
	 @Autowired
	 LinesDAO linesDAO;
	 
	 @Autowired
	 LinesUserInputFilter linesFilter;
	 
	 @Autowired
	 @Qualifier("ServerOrigin")
	 ServerOrigin origin;
	 
	 @GET
	 @Path("/lines/{element}/")
	 @Produces("application/json")
	 public Response getLinesForElement(@PathParam("element") String element, @QueryParam("wlFrom") String wlFromS, @QueryParam("wlTo") String wlToS){
		  
		 System.out.println("origin is configured to a value of [" + origin.getUrl() + "]");
		 
		 //element is OK?
		 if (element == null){
			 return Response.status(Response.Status.BAD_REQUEST).
					 header("Access-Control-Allow-Origin", origin.getUrl()).
					 header("Access-Control-Allow-Methods", "GET").
					 header("Access-Control-Allow-Headers","x-requested-with").
					 allow("OPTIONS").
					 build();
		 } else if (!linesFilter.getAllowedElements().contains(element)){
			 return Response.status(Response.Status.NOT_FOUND).
					 header("Access-Control-Allow-Origin", origin.getUrl()).
					 header("Access-Control-Allow-Methods", "GET").
					 header("Access-Control-Allow-Headers","x-requested-with").
					 allow("OPTIONS").
					 build();
		 } 
		
		 List<Line> lines;
		 if (wlFromS == null && wlToS == null){
			 
			 //optional wl not supplied, OK, return all lines for element
			 lines = linesDAO.getLinesByElement(element);
			 return Response.
					 ok().
					 entity(lines).
					 header("Access-Control-Allow-Origin", origin.getUrl()).
					 header("Access-Control-Allow-Methods", "GET").
					 header("Access-Control-Allow-Headers","x-requested-with").
					 allow("OPTIONS").
					 build(); 
		 } else if (wlFromS != null && wlToS == null){
			 
			 try {
				
				 Double wlFrom;
				 wlFrom = Double.valueOf(wlFromS);

					 
				 lines = linesDAO.getLinesByElementAndStartWl(element, wlFrom);
				 return Response.
						 ok().
						 entity(lines).
						 header("Access-Control-Allow-Origin", origin.getUrl()).
						 header("Access-Control-Allow-Methods", "GET").
						 header("Access-Control-Allow-Headers","x-requested-with").
						 allow("OPTIONS").
						 build();
					 	 
			 } catch (NumberFormatException e) {
				 return Response.status(Response.Status.BAD_REQUEST).
						 header("Access-Control-Allow-Origin", origin.getUrl()).
						 header("Access-Control-Allow-Methods", "GET").
						 header("Access-Control-Allow-Headers","x-requested-with").
						 allow("OPTIONS").
						 build();
			 }
			 
		 } else if (wlFromS == null && wlToS != null){
			 
			 try {
				 
				 Double wlTo;
				 wlTo = Double.valueOf(wlToS);
					 
				 lines = linesDAO.getLinesByElementAndEndWl(element, wlTo);
				 return Response.
						 ok().
						 entity(lines).
						 header("Access-Control-Allow-Origin", origin.getUrl()).
						 header("Access-Control-Allow-Methods", "GET").
						 header("Access-Control-Allow-Headers","x-requested-with").
						 allow("OPTIONS").
						 build();
					 	 
			 } catch (NumberFormatException e) {
				 
				 //or bad request if wavelengths are not numbers 
				 return Response.
						 status(Response.Status.BAD_REQUEST).
						 header("Access-Control-Allow-Origin", origin.getUrl()).
						 header("Access-Control-Allow-Methods", "GET").
						 header("Access-Control-Allow-Headers","x-requested-with").
						 allow("OPTIONS").build();
			 }
			 
		 } else {
			 try {
				 
				 //return requested wl range 
				 Double wlFrom, wlTo;
				 wlFrom = Double.valueOf(wlFromS);
				 wlTo = Double.valueOf(wlToS);
					 
				 lines = linesDAO.getLinesByElementListAndWlFromAndWlTo(Arrays.asList(element), wlFrom, wlTo);
				 return Response.
						 ok().
						 entity(lines).
						 header("Access-Control-Allow-Origin", origin.getUrl()).
						 header("Access-Control-Allow-Methods", "GET").
						 header("Access-Control-Allow-Headers","x-requested-with").
						 allow("OPTIONS").
						 build();
					 	 
			 } catch (NumberFormatException e) {
				 
				 //or bad request if wavelengths are not numbers 
				 return Response.status(Response.Status.BAD_REQUEST).
						 header("Access-Control-Allow-Origin", origin.getUrl()).
						 header("Access-Control-Allow-Methods", "GET").
						 header("Access-Control-Allow-Headers","x-requested-with").
						 allow("OPTIONS").build();
			 }
		 }
	 }	 
}
