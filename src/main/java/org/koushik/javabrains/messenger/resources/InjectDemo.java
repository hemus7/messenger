package org.koushik.javabrains.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("injectDemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemo {
	
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param")String matrixParam,
			@HeaderParam("customheader")String header,
			@CookieParam("cookie")String cookie){
		return "Matrix param: "+matrixParam+" Header : "+header+" Cookie : "+cookie;
	}
	
	@GET
	@Path("context")
	public String getparametersUsingContext(@Context UriInfo uriInfo,
			@Context HttpHeaders headers){
		return uriInfo.getAbsolutePath().toString()+" headers "+headers.getCookies().toString();
	}

}
