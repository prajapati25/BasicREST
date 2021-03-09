package com.nttdata.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nttdata.messanger.database.DatabaseClass;
import com.nttdata.messanger.model.Profile;

public class ProfileService {
	
	public ProfileService() {
		profiles.put("Himanshu", new Profile(1, "Himanshu", "Himanshu", "Prajapati"));
		profiles.put("peter", new Profile(2, "peter", "peter", "peter"));
	}
	
	private Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public List<Profile> getAllProfiles() {

		return new ArrayList<Profile>(profiles.values());

	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;

	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}

}
