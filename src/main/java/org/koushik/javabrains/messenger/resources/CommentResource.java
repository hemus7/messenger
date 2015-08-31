package org.koushik.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.koushik.javabrains.messenger.model.Comment;
import org.koushik.javabrains.messenger.service.CommentService;
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CommentResource {/*
	@GET
	public String getComment(){
		return "from sub resource";
	}
	
	@GET
	@Path("/{commentId}")
	public String getCommentId(@PathParam("messageId")long messageId,
			@PathParam("commentId")long commentId){
		return "Comment :"+commentId+ " messageId : "+messageId;
	}
	*/
	private CommentService cs=new CommentService();
	@GET
	public List<Comment> getAllComment(@PathParam("messageId")long messageId){
		return cs.getAllComments(messageId); 
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam ("messageId")long messageId,@PathParam ("commentId")long commentId){
		return cs.getComment(messageId, commentId);
	}
	
	
	@POST
	public Comment addComment(@PathParam ("messageId")long messageId,Comment c){
		return cs.addComment(messageId, c);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam ("messageId")long messageId,@PathParam ("commentId")long commentId,Comment c){
		c.setId(commentId);
		return cs.updateComment(messageId, c);
		
	}
	
	
	@DELETE
	@Path("/{commentId}")
	public Comment deleteComment(@PathParam ("messageId")long messageId,@PathParam ("commentId")long commentId){
		return cs.removeComment(messageId, commentId);
	}
}
