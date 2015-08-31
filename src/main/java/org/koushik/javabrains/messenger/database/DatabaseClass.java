package org.koushik.javabrains.messenger.database;

import java.util.HashMap;

import org.koushik.javabrains.messenger.model.Comment;
import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.model.Profile;

public class DatabaseClass {
	private static HashMap<Long, Message> messages=new HashMap<>();
	private static HashMap<String, Profile> profiles=new HashMap<>();
	private static HashMap<Long, Comment> comments=new HashMap<>();
	public static HashMap<Long, Comment> getComments() {
		return comments;
	}
	public static HashMap<Long, Message> getMessages() {
		return messages;
	}
	public static HashMap<String, Profile> getProfiles() {
		return profiles;
	}

}
