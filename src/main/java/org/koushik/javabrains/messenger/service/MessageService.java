package org.koushik.javabrains.messenger.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.koushik.javabrains.messenger.database.DatabaseClass;
import org.koushik.javabrains.messenger.exception.DataNotFoundException;
import org.koushik.javabrains.messenger.exception.IGS_TestException;
import org.koushik.javabrains.messenger.model.Comment;
import org.koushik.javabrains.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public List<Message> getAllMessages() {
		//System.out.println("YEAR==ALL====>");
		return new ArrayList<>(messages.values());
	}

	public List<Message> getAllMessagesForYear(int year) {
		//System.out.println("YEAR==MET====>"+year);

		List<Message> messageList = new ArrayList<>(messages.values());
		
		Calendar cal = Calendar.getInstance();
		for (Message m : messages.values()) {
			cal.setTime(m.getCreated());

			if (cal.get(Calendar.YEAR) == year) {
				messageList.add(m);
			}
		}
		return messageList;
	}
	
	public List<Message> getAllMessagesPaginate(int start,int size) {
		//System.out.println("YEAR==MET====>"+year);

		List<Message> messageList = new ArrayList<>(messages.values());
		
		
		for (Message m : messages.values()) {

			if (m.getId()>=start && m.getId() <(start+size)) {
				messageList.add(m);
			}
		}
		return messageList;
	}

	public Message getMessage(long id) {
		Message newMessage=messages.get(id);
		if(newMessage==null){
			throw new DataNotFoundException("Message is not in the system : "+id);
		}
		return newMessage;
	}

	public Message addMessage(Message m) {
		m.setId(messages.size() + 1);
		messages.put(m.getId(), m);
		return m;
	}

	public Message updateMessage(Message m) {
		if (m.getId() <= 0) {
			return null;
		}
		messages.put(m.getId(), m);
		return m;
	}

	public Message removeMessage(long id) {
		return messages.remove(id);

	}
	
	public Response testIGS(UriInfo uriInfo,String newId,Message newMessage) {
		boolean check=true;
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		if(check){
			throw new IGS_TestException(":: TEST IGS SERVICE EXCEPTION ::");
		}
		return Response.created(uri).entity(newMessage).build();
	}

	public MessageService() {
		super();
		Map<Long,Comment> comments=new HashMap<>();
		Comment com1=new Comment(1L, "Test comment", new Date(), "hemu");
		Comment com2=new Comment(2L, "Test comment", new Date(), "ammu");
		Comment com3=new Comment(3L, "Test comment", new Date(), "jo");
		comments.put(1L, com1);
		comments.put(2L, com2);
		comments.put(3L, com3);
		messages.put(1L, new Message(1L, "Message 1", new Date(), "Hemanth",(HashMap<Long, Comment>) comments));
		messages.put(2L, new Message(2L, "Message 2", new Date(), "Jyothi",(HashMap<Long, Comment>) comments));
		messages.put(3L, new Message(3L, "Message 3", new Date(), "Ammu",(HashMap<Long, Comment>) comments));

	}

}
