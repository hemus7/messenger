package org.koushik.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.koushik.javabrains.messenger.database.DatabaseClass;
import org.koushik.javabrains.messenger.model.Comment;
import org.koushik.javabrains.messenger.model.ErrorMessage;
import org.koushik.javabrains.messenger.model.Message;

public class CommentService {
private Map<Long,Message> messages=DatabaseClass.getMessages();
	

	public List<Comment> getAllComments(long messageId){
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId,long commentId){
		ErrorMessage msg=new ErrorMessage("Not found", 404, "http://google.com");
		Response resp= Response.status(Status.NOT_FOUND).entity(msg).build();
		Message comMessage=messages.get(messageId);
		if(comMessage==null){
			throw new WebApplicationException(resp);
		}
		
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		Comment comment=comments.get(commentId);
		if(comment==null){
			throw new NotFoundException(resp);
		}
		return comment; 
	}
	
	
	public Comment addComment(long messageId,Comment c){
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		c.setId(comments.size()+1);
		comments.put(c.getId(),c);
		return c;
	}
	

	public Comment updateComment(long messageId,Comment c){
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		if(c.getId()<=0){
			return null;
		}
		comments.put(c.getId(), c);
		return c;
	}
	
	
	
	public Comment removeComment(long messageId,long commentId){
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		return comments.remove(commentId);
	}


}
