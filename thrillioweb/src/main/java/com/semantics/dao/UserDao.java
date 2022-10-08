package com.semantics.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.semantics.DataStore;
import com.semantics.constant.Gender;
import com.semantics.constant.UserType;
import com.semantics.entities.User;
import com.semantics.manager.UserManager;

public class UserDao {
	public List<User> getUsers() {
		return DataStore.getUsers();
	}

	public User getUser(long userId) {
		User user = null;

		
		  try { 
			  Class.forName("com.mysql.cj.jdbc.Driver");
		  // new com.mysql.jdbc.Driver();
		  // OR System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		  
		  // OR java.sql.DriverManager
		  // DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		  } catch (ClassNotFoundException e) {
		  e.printStackTrace();
		  }
		 

		//System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

		try (Connection conn = java.sql.DriverManager
				.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false", "root", "password");
				Statement stmt = conn.createStatement();) {
			String query = "Select * from User where id = " + userId;
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int gender_id = rs.getInt("gender_id");
				Gender gender = Gender.values()[gender_id];
				int user_type_id = rs.getInt("user_type_id");
				UserType userType = UserType.values()[user_type_id];
				int kid_friendlyStatus = rs.getInt("kid_friendly");
				String kid_friendly = "Not-Active";
				if (kid_friendlyStatus == 1)
					kid_friendly = "Active";

				user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType,
						kid_friendly);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

	public long authenticate(String email, String password,int userType) {

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			// new com.mysql.jdbc.Driver();
			// OR System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");

			// OR java.sql.DriverManager // DriverManager.registerDriver(new
			// com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

		try (Connection conn = java.sql.DriverManager
				.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false", "root", "password");
				Statement stmt = conn.createStatement();) {

			String query = "Select id from user where email = '" + email + "' and password = '" + password + "' and user_type_id ='"+userType+"'";

			System.out.println("query: " + query);
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				return rs.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public long checkEmail(String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "Select id from user where email = '" + email + "'";

			System.out.println("query: " + query);
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				return rs.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public void newUser(String email, String password, String first_name, String last_name, int gender_id, int userType,
			int kid_Friendly) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "INSERT INTO User (email, password, first_name,last_name, gender_id, user_type_id, created_date, kid_Friendly)"
					+ " VALUES ('" + email + "' ,'" + password + "' ,'" + first_name + "' ,'" + last_name + "' ,"
					+ gender_id + " ," + userType + " , NOW() ," + kid_Friendly + ")";

			System.out.println("query (update sharedbyStatus): " + query);
			int rs = stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void changePassword(long userId, String Npassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "Update user set password = '" + Npassword + "' where id = " + userId;

			System.out.println("query: " + query);
			int rs = stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void changeKidFriendly(long userId, int status) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "Update user set kid_friendly = " + status + " where id = " + userId;

			System.out.println("query: " + query);
			int rs = stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
