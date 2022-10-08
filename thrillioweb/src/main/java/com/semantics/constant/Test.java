package com.semantics.constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.semantics.entities.Bookmark;
import com.semantics.manager.BookmarkManager;

public class Test {
	public static long authenticate(String email, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false", "root", "password");
				Statement stmt = conn.createStatement();) {	
			String query = "Select id from User where email = '" + email + "' and password = '" + password + "'";
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
	
	public static void newUser(String email, String password, String first_name, String last_name, int gender_id,
			int userType) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "INSERT INTO User (email, password, first_name,last_name, gender_id, user_type_id, created_date)"
					+ " VALUES ('"+email+"' ,'"+password+"' ,'"+first_name+"' ,'"+last_name+"' ,"+gender_id+" ,"+userType+ " , NOW())";
					 
					
			System.out.println("query (update sharedbyStatus): " + query);
			stmt.executeUpdate(query);
						
	    				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void check() {
		 Bookmark bookmark = BookmarkManager.getInstance().getMovie(Long.parseLong("4"));
		 System.out.println(bookmark);
		 String s="Classics";
		 
	}

	public static void main(String[] args) {
		int u=UserType.USER.ordinal();
		int g=Gender.TRANSGENDER.ordinal();
		//System.out.println(u+" "+g);
		//newUser("amit@gmail.com","amit","Amit","Singh",0,1);
		//System.out.println(authenticate("anit@gmail.com","amit"));
		Test test=new Test();
		test.check();
		
	}
}
