package com.nttdata.messanger.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.nttdata.messanger.model.ErrorMessage;

@Provider
public class GenricExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errMsg = new ErrorMessage(ex.getMessage(), 500, "prajapatihimansu178@gmail.com");

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errMsg).build();
	}

}
