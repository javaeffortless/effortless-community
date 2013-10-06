package org.effortless.tests.model;

import java.io.IOException;

import org.effortless.model.LogData;
import org.effortless.model.StartupDb;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestProfiles {

	public static void main (String[] args) {
		try {
			setupDs();
			
			UserProfile profile = new UserProfile();
			profile.setName("NOMBRE " + System.currentTimeMillis());
			UserProfile.UserProfilePermission p = new UserProfile.UserProfilePermission();
			p.setPermission(Permission.DELETE);
			profile.getPermissions().add(p);
			
			profile.persist();
			
			User user = new User();
			user.setName("Jesús " + System.currentTimeMillis());
			user.setProfile(profile);
			User.UserPermission p2 = new User.UserPermission();
			p2.setPermission(Permission.PRINT);
			user.getPermissions().add(p2);
			user.persist();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	protected static void setupDs() {
		try {
			StartupDb.addAppEntity(UserProfile.class.getName());
			StartupDb.addAppEntity(UserProfile.UserProfilePermission.class.getName());
			StartupDb.addAppEntity(User.class.getName());
			StartupDb.addAppEntity(User.UserPermission.class.getName());
			StartupDb.addAppEntity(LogData.class.getName());
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StartupDb.runAllDb();
		} catch (ValidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String jndi = "gy:/hibernate/model/SessionFactory";
//		javax.naming.InitialContext ictx = new javax.naming.InitialContext();
//		result = (SessionFactory)ictx.lookup(jndi);
	}
	
}
