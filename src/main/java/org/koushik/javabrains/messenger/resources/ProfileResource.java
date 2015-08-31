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

import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.model.Profile;
import org.koushik.javabrains.messenger.service.MessageService;
import org.koushik.javabrains.messenger.service.ProfileService;

@Path("profiles")
public class ProfileResource {
	private ProfileService ps=new ProfileService();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Profile> getProfiles(){
		return ps.getAllProfiles();
	}
	
	@GET
	@Path("/{profileName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Profile getProfile(@PathParam ("profileName")String profileName){
		return ps.getProfile(profileName); 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile addProfilee(Profile p){
		return ps.addProfile(p);
	}
	
	@PUT
	@Path("/{profileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile updateMessage(Profile p,@PathParam ("profileName")String profileName){
		p.setProfileName(profileName);
		return ps.updateProfile(p);
	}
	
	
	@DELETE
	@Path("/{profileName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Profile deleteProfile(@PathParam ("profileName")String profileName){
		return ps.removeProfile(profileName);
	}

}
