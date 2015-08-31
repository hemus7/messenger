package org.koushik.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.koushik.javabrains.messenger.database.DatabaseClass;
import org.koushik.javabrains.messenger.model.Profile;

public class ProfileService {
	private Map<String, Profile> profiles=DatabaseClass.getProfiles();
	public ArrayList<Profile> getAllProfiles(){
	/*	List<Message> messageList=new ArrayList<>();
		messageList.add(new Message(1L, "Hello world", new Date(), "Koushik"));
		messageList.add(new Message(2L, "Hello rest", new Date(), "Richard"));*/
		
		return new ArrayList<>(profiles.values());
	}
	
	public Profile getProfile(String name){
		return profiles.get(name);
	}
	
	public Profile addProfile(Profile p){
		p.setId(profiles.size()+1);
		profiles.put(p.getProfileName(), p);
		return p;
	}
	
	public Profile updateProfile(Profile p){
		if(p.getProfileName().isEmpty()){
			return null;
		}
		profiles.put(p.getProfileName(), p);
		return p;
	}
	
	public Profile removeProfile(String name){
		return profiles.remove(name);
	}

	public ProfileService() {
		super();
		profiles.put("Hemu", new Profile(1L, "Hemu", new Date(), "Hemanth", "KE"));
		profiles.put("Ammu", new Profile(2L, "Ammu", new Date(), "Ammu", "AY"));
		profiles.put("Jo", new Profile(3L, "Jo", new Date(), "Jo", "De"));
		
		
		
	}

}
