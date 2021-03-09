package com.nttdata.messanger.MessageResource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.nttdata.messanger.MessageResource.beanParamFilter.Beanfilter;
import com.nttdata.messanger.model.Message;
import com.nttdata.messanger.model.Links;
import com.nttdata.messanger.service.MessageService;

@Path("/messages")
public class MessageResource {
	MessageService messageService = new MessageService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@BeanParam Beanfilter beanParam)

	{
		if (beanParam.getYear() > 0)
			return messageService.getMessagesByYear(beanParam.getYear());
		if (beanParam.getSize() > 0 && beanParam.getStart() > 0)
			return messageService.getMessagesPaginated(beanParam.getSize(), beanParam.getSize());
		return messageService.getAllMessages();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message msg = messageService.addMessage(message);
		String newId = String.valueOf(msg.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(msg).build();

	}

	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);

	}

	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {

		Message message = messageService.getMessage(id);
		String SelfUri = buildSelfUri(uriInfo, message.getId());
		String profileUri = buildProfileUri(uriInfo, message.getAuthor());
		String commentUri = buildCommentUri(uriInfo, message.getId());
	
		message.getLink().add(new Links(SelfUri, "self"));
		message.getLink().add(new Links(profileUri, "profile"));
		message.getLink().add(new Links(commentUri, "comments"));

		return Response.status(Status.FOUND).entity(message).build();
	}

	private String buildSelfUri(UriInfo uriInfo, long messageId) {
		return uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(messageId))
				.build().toString();		
	}
	private String buildProfileUri(UriInfo uriInfo, String author) {
		return uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(author)
				.build().toString();	
	}
	private String buildCommentUri(UriInfo uriInfo, long messageId) {
		return uriInfo.getBaseUriBuilder()
				.path(MessageResource.class,"getcomments")	
				.resolveTemplate("messageId", messageId).build().toString();	
	}

	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message deleteMessage(@PathParam("messageId") long id) {
		return messageService.removeMessages(id);

	}

	@Path("/{messageId}/comments")
	public CommentResource getcomments() {
		return new CommentResource();

	}

}
