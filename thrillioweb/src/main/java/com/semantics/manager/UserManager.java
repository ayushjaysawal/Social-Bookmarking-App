package com.semantics.manager;

import java.util.List;

import com.semantics.constant.Gender;
import com.semantics.constant.UserType;
import com.semantics.dao.UserDao;
import com.semantics.entities.User;
import com.semantics.util.StringUtil;

public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao=new UserDao();

	private UserManager() {
	}

	public static UserManager getInstance() {
		return instance;
	}
	public User createUser(long id,String email,String password,String firstName,String lastName,Gender gender,UserType userType,String kid_friendly) {
		User user =new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setGender(gender);
		user.setUserType(userType);
		user.setKid_friendly(kid_friendly);
		
		return user;
	}
	public List<User> getUsers() {
		return dao.getUsers();
	}

	public User getUser(long userId) {
		// TODO Auto-generated method stub
		
		return dao.getUser(userId);
		
	}

	public long authenticate(String email, String password, String user) {
		// TODO Auto-generated method stub
		int userType=0;
		if(user.equals("Editor")) {
			userType=1;
		}
		return dao.authenticate(email,StringUtil.encodePassword(password),userType);
		
	}

	public void newUser(String email, String password, String first_name, String last_name, String gender,String kidFriendly) {
		// TODO Auto-generated method stub
		int userType=UserType.USER.ordinal();
		int gender_id=0;
		
		if(gender.equalsIgnoreCase("female")) {
			gender_id=1;
		}
		else if(gender.equalsIgnoreCase("transgender"))
			gender_id=2;
		int kid_Friendly=0;
		if(kidFriendly.equals("On")) kid_Friendly=1;
		dao.newUser(email,StringUtil.encodePassword(password),first_name,last_name,gender_id,userType,kid_Friendly);
		
	}

	public void changePassword(long userId, String Npassword) {
		dao.changePassword(userId,StringUtil.encodePassword(Npassword));
	}

	public void changeKidFriendly(long userId, int status) {
		dao.changeKidFriendly(userId,status);
	}

	public long checkEmail(String email) {
		
		return dao.checkEmail(email);
	}
}
