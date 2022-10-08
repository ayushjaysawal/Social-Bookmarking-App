package com.semantics.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semantics.manager.UserManager;

/**
 * Servlet implementation class AuthController
 */
@WebServlet(urlPatterns ={"/auth","/auth/logout","/register"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet path: "+request.getServletPath());
		if(!request.getServletPath().contains("logout")) {
			
			if(!request.getServletPath().contains("register")) {
				//request.getRequestDispatcher("/register.jsp").forward(request, response);
				//login
				String email=request.getParameter("email");
				String password=request.getParameter("password");
				String user=request.getParameter("userType");
				long userId=UserManager.getInstance().authenticate(email,password,user);
			    if(userId!=-1) {
			    	HttpSession session=request.getSession(); // session id
			    	session.setAttribute("userId", userId);
			    	if(user.equals("User"))
			    	request.getRequestDispatcher("/bookmark/mybooks").forward(request, response);
			    	else
			    	request.getRequestDispatcher("/bookmark/request").forward(request, response);
			    }
				
			    else {
			    	request.setAttribute("errorMessage", "Invalid user or password");
			    	request.getRequestDispatcher("/login.jsp").forward(request, response);
			    }
			}
			else {
				//request.getRequestDispatcher("/register.jsp").forward(request, response);
		    String email=request.getParameter("email");
		    if(UserManager.getInstance().checkEmail(email)!=-1) {
			String password=request.getParameter("password");
			String first_name=request.getParameter("first_name");
			String last_name=request.getParameter("last_name");
			String gender=request.getParameter("gender");
			String kidFriendly=request.getParameter("kidFriendly");
			UserManager.getInstance().newUser(email,password,first_name,last_name,gender,kidFriendly);
			long userId=UserManager.getInstance().authenticate(email,password,"User");
			if(userId!=-1) {
		    	HttpSession session=request.getSession(); // session id
		    	session.setAttribute("userId", userId);
		    	
		    	request.getRequestDispatcher("/bookmark/mybooks").forward(request, response);
		    }
		    else {
		    	request.getRequestDispatcher("/login.jsp").forward(request, response);
		    }
		
		}
		    else {
		    	request.setAttribute("errorMessage", "This email already in used");
		    	request.getRequestDispatcher("/register.jsp").forward(request, response);
		    }		
		}
		}
		else {
			request.getSession().invalidate();
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
