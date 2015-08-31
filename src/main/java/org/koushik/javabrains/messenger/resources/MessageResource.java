package org.koushik.javabrains.messenger.resources;

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
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.resources.beans.MessageFilterBean;
import org.koushik.javabrains.messenger.service.MessageService;

@Path("messages")
public class MessageResource {
	private MessageService ms=new MessageService();
	private static final Logger logger = LogManager.getLogger(MessageResource.class);
	@GET
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	public List<Message> getMessages(@BeanParam MessageFilterBean filter){
		if(filter.getYear()>0){
			//System.out.println("YEAR==Res====>"+year);
			return ms.getAllMessagesForYear(filter.getYear());
		}
		if(filter.getStart()>0&&filter.getSize()>0){
			//System.out.println("YEAR==Res====>"+year);
			return ms.getAllMessagesPaginate(filter.getStart(),filter.getSize());
		}
		logger.error("TEST ERROR FOR MESSAGES default");
		ThreadContext.put("ROUTINGKEY", "special");
		logger.error("TEST ERROR FOR MESSAGES special");
		ThreadContext.clearAll();
		return ms.getAllMessages();
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		ThreadContext.put("ROUTINGKEY", "second");
		logger.error("TEST ERROR FOR COMMENTS second");
		ThreadContext.clearAll();
		logger.error("TEST ERROR FOR COMMENTS default");
		return new CommentResource();
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam ("messageId")long messageId,
			@Context UriInfo uriInfo){
		Message objMessage=ms.getMessage(messageId);
		
		String uri = getUriForSelf(uriInfo, objMessage);		
		objMessage.addLink(uri,"self");
		
		uri = getUriForProfile(uriInfo, objMessage);
		objMessage.addLink(uri,"profile");
		
		uri = getUriForComments(uriInfo, objMessage);
		objMessage.addLink(uri,"comments");
		
		return objMessage; 
	}

	private String getUriForSelf(UriInfo uriInfo, Message objMessage) {
		String uri=uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(objMessage.getId()))
				.build().toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message objMessage) {
		String uri=uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path((objMessage.getAuthor()))
				.build().toString();
		return uri;
	}
	
	private String getUriForComments(UriInfo uriInfo, Message objMessage) {
		String uri=uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class,"getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", objMessage.getId())
				.build().toString();
		return uri;
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	public Response addMessage(Message m,@Context UriInfo uriInfo){
		Message newMessage=ms.addMessage(m);
		String newId=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
		 
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(Message m,@PathParam ("messageId")long messageId){
		m.setId(messageId);
		return ms.updateMessage(m);
	}
	
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message deleteMessage(@PathParam ("messageId")long messageId){
		return ms.removeMessage(messageId);
	}
	
	@GET
	@Path("/IGS/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testIGSMapper(@PathParam ("messageId")long messageId,@Context UriInfo uriInfo){
		/*Message newMessage=ms.addMessage(m);
		String newId=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();*/
		Message newMessage=ms.getMessage(messageId);
		String newId=String.valueOf(newMessage.getId());
		//return testIGSMapper(uriInfo,newId,newMessage);
		return ms.testIGS(uriInfo, newId, newMessage);
	}

}
