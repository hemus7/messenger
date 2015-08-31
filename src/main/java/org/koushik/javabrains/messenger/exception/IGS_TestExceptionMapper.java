package org.koushik.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.koushik.javabrains.messenger.model.ErrorMessage;
@Provider
public class IGS_TestExceptionMapper implements ExceptionMapper<IGS_TestException> {

	@Override
	public Response toResponse(IGS_TestException exception) {
		// TODO Auto-generated method stub
		ErrorMessage msg=new ErrorMessage(exception.getMessage(), 404, "http://google.com");
		return Response.status(Status.NOT_FOUND).entity(msg).build();
	}

}
