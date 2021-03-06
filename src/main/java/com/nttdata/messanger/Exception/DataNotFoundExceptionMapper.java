package com.nttdata.messanger.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.nttdata.messanger.model.ErrorMessage;
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException > {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errMsg = new ErrorMessage(ex.getMessage(),404,"prajapatihimansu178@gmail.com");
		
		return Response.status(Status.NOT_FOUND)
				.entity(errMsg)
				.build();
	}

}
