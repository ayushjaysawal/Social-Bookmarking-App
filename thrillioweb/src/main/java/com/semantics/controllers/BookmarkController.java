package com.semantics.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semantics.constant.KidFriendlyStatus;
import com.semantics.entities.Bookmark;
import com.semantics.entities.User;
import com.semantics.manager.BookmarkManager;
import com.semantics.manager.UserManager;
import com.semantics.util.StringUtil;

@WebServlet(urlPatterns = { "/bookmark", "/bookmark/save", "/bookmark/mybooks", "/bookmark/movie", "/bookmark/remove", "/bookmark/account",
		"/bookmark/changePassword", "/bookmark/changeKidFriendly","/bookmark/searchbook","/bookmark/searchmovie","/bookmark/request","/bookmark/addBooks",
		"/bookmark/addMovies","/bookmark/addUserBook","/bookmark/addUserMovie","/bookmark/udeletebook","/bookmark/udeletemovie"})
public class BookmarkController extends HttpServlet {
	/*
	 * private static BookmarkController instance =new BookmarkController(); private
	 * BookmarkController() {} public static BookmarkController getInstance() {
	 * return instance; }
	 */

	public BookmarkController() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		System.out.println("Servlet path :" + request.getServletPath());
		if (request.getSession().getAttribute("userId") != null) {
			long userId = (long) request.getSession().getAttribute("userId");

			if (request.getServletPath().contains("save")) {
				// save
				dispatcher = request.getRequestDispatcher("/bookmark/mybooks");

				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = null;
				if (request.getParameter("bid") != null) {
					String bid = request.getParameter("bid");
					bookmark = BookmarkManager.getInstance().getBook(Long.parseLong(bid));

				}

				else if (request.getParameter("mid") != null) {
					String mid = request.getParameter("mid");
					bookmark = BookmarkManager.getInstance().getMovie(Long.parseLong(mid));

				}

				BookmarkManager.getInstance().saveUserBookmark(user, bookmark);

				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
				Collection<Bookmark> list2 = BookmarkManager.getInstance().getMovies(true, userId);
				request.setAttribute("books", list);
				request.setAttribute("movies", list2);

			} else if (request.getServletPath().contains("remove")) {
				dispatcher = request.getRequestDispatcher("/bookmark/mybooks");

				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = null;
				if (request.getParameter("bid") != null) {
					String bid = request.getParameter("bid");
					BookmarkManager.getInstance().removeBook(Long.parseLong(bid),userId);

				}

				else if (request.getParameter("mid") != null) {
					String mid = request.getParameter("mid");
					BookmarkManager.getInstance().removeMovie(Long.parseLong(mid),userId);

				}

				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
				Collection<Bookmark> list2 = BookmarkManager.getInstance().getMovies(true, userId);
				request.setAttribute("books", list);
				request.setAttribute("movies", list2);
			}
			else if (request.getServletPath().contains("udeletebook")) {
				dispatcher = request.getRequestDispatcher("/bookmark/mybooks");

				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = null;
				if (request.getParameter("bid") != null) {
					String bid = request.getParameter("bid");
					BookmarkManager.getInstance().removeUserBook(Long.parseLong(bid),userId);

				}

			}
			else if (request.getServletPath().contains("udeletemovie")) {
				dispatcher = request.getRequestDispatcher("/bookmark/mybooks");

				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = null;
				 if (request.getParameter("mid") != null) {
					String mid = request.getParameter("mid");
					BookmarkManager.getInstance().removeUserMovie(Long.parseLong(mid),userId);

				}

			}

			else if (request.getServletPath().contains("account")) {
				// mybooks
				dispatcher = request.getRequestDispatcher("/account.jsp");

				User list = UserManager.getInstance().getUser(userId);
				request.setAttribute("accounts", list);
				

			}
			else if(request.getServletPath().contains("changePassword")) {
				dispatcher = request.getRequestDispatcher("/changePassword.jsp");
				String Cpassword=request.getParameter("Cpassword");
				Cpassword=StringUtil.encodePassword(Cpassword);
				String Npassword=request.getParameter("Npassword");
				String Rpassword=request.getParameter("Rpassword");
				User list = UserManager.getInstance().getUser(userId);
				if(Cpassword.equals(list.getPassword())&&Npassword.equals(Rpassword)) {
						
						dispatcher = request.getRequestDispatcher("/account.jsp");
						UserManager.getInstance().changePassword(userId,Npassword);
						request.setAttribute("accounts", list);
				}
				else {
					dispatcher = request.getRequestDispatcher("/changePassword.jsp");
					request.setAttribute("errorMessage", "Password not Matched");
				}
			}
			else if(request.getServletPath().contains("changeKidFriendly")) {
				dispatcher=request.getRequestDispatcher("/account.jsp");
				String pass=request.getParameter("Cpassword");
				pass=StringUtil.encodePassword(pass);
				User list1=UserManager.getInstance().getUser(userId);
				if(list1.getPassword().equals(pass)) {
				int status=1;
				if(list1.getKid_friendly().equals("Active")) status =0;
				UserManager.getInstance().changeKidFriendly(userId,status);
				User list= UserManager.getInstance().getUser(userId);
				request.setAttribute("accounts", list);
				}
				else {
					dispatcher=request.getRequestDispatcher("/changeKidFriendly.jsp");
					request.setAttribute("errorMessage", "Incorrect password");
				}
			
			}
			else if(request.getServletPath().contains("request")) {
				dispatcher = request.getRequestDispatcher("/requests.jsp");

				Collection<Bookmark> list3=BookmarkManager.getInstance().getAllUserBooks();
				request.setAttribute("userbooks", list3);
				
				Collection<Bookmark> list4=BookmarkManager.getInstance().getAllUserMovies();
				request.setAttribute("usermovies", list4);
			}
			else if (request.getServletPath().contains("mybooks")) {
				// mybooks
				dispatcher = request.getRequestDispatcher("/saved.jsp");

				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
				request.setAttribute("books", list);

				Collection<Bookmark> list2 = BookmarkManager.getInstance().getMovies(true, userId);

				request.setAttribute("movies", list2);
				
				Collection<Bookmark> list3=BookmarkManager.getInstance().getUserBooks(userId);
				request.setAttribute("userbooks", list3);
				
				Collection<Bookmark> list4=BookmarkManager.getInstance().getUserMovies(userId);
				request.setAttribute("usermovies", list4);
			}
			else if (request.getServletPath().contains("addBooks")) {
				// mybooks
				dispatcher = request.getRequestDispatcher("/addBook.jsp");

				String title=request.getParameter("title");
				String image=request.getParameter("image");
				String publicationYear=request.getParameter("publicationYear");
				String publisher=request.getParameter("publisher");
				String author=request.getParameter("authorName");
				String bookGenre=request.getParameter("genre");
				String kidFriendly=request.getParameter("kidFriendly");
				String bookRating=request.getParameter("bookRating");
				if(BookmarkManager.getInstance().addBooks(title,image,publicationYear,publisher,author,bookGenre,kidFriendly,bookRating)) {
					request.setAttribute("errorMessage", "Book Added");
				}
				else {
					request.setAttribute("errorMessage", "Book Not Added Insert Again");
				}
			}
			else if (request.getServletPath().contains("addUserBook")) {
				// mybooks
				dispatcher = request.getRequestDispatcher("/bookmark/mybooks");

				String title=request.getParameter("title");
				String publicationYear=request.getParameter("publishYear");
				String authors=request.getParameter("author");
				if(BookmarkManager.getInstance().addUserBooks(title,publicationYear,authors,userId)) {
					request.setAttribute("errorMessage", "Book Added");
				}
				else {
					request.setAttribute("errorMessage", "Book Not Added Insert Again");
				}
			}
			else if (request.getServletPath().contains("addUserMovie")) {
				// mybooks
				dispatcher = request.getRequestDispatcher("/bookmark/mybooks");

				String title=request.getParameter("title");
				String releaseYear=request.getParameter("releaseYear");
				String directors=request.getParameter("director");
				if(BookmarkManager.getInstance().addUserMovies(title,releaseYear,directors,userId)) {
					request.setAttribute("errorMessage", "Book Added");
				}
				else {
					request.setAttribute("errorMessage", "Book Not Added Insert Again");
				}
			}
			else if (request.getServletPath().contains("addMovies")) {
				// mybooks
				dispatcher = request.getRequestDispatcher("/addMovie.jsp");

				String title=request.getParameter("title");
				String image=request.getParameter("image");
				String releaseYear=request.getParameter("releaseYear");
				String actors=request.getParameter("actor");
				String directors=request.getParameter("director");
				String movieGenre=request.getParameter("genre");
				String kidFriendly=request.getParameter("kidFriendly");
				String movieRating=request.getParameter("movieRating");
				if(BookmarkManager.getInstance().addMovies(title,image,releaseYear,actors,directors,movieGenre,kidFriendly,movieRating)) {
					request.setAttribute("errorMessage", "Book Added");
					
				}
				else {
					request.setAttribute("errorMessage", "Book Not Added Insert Again");
				}
			}
			else if(request.getServletPath().contains("searchmovie")){
				dispatcher=request.getRequestDispatcher("/movie.jsp");
				String searchBy=request.getParameter("searchBy");
				if(searchBy.equals("Title")) {
				String name=request.getParameter("Mname");
				Collection<Bookmark> list=BookmarkManager.getInstance().searchMovies(false,userId,name,1);
				request.setAttribute("movies", list);
				}
				else {
					String rating=request.getParameter("Mname");
					Collection<Bookmark> list=BookmarkManager.getInstance().searchMovies(false,userId,rating,0);
					request.setAttribute("movies", list);
				}
			}
			else if(request.getServletPath().contains("searchbook")){
				dispatcher=request.getRequestDispatcher("/browse.jsp");
				String searchBy=request.getParameter("searchBy");
				if(searchBy.equals("Title")) {
				String name=request.getParameter("Bname");
				Collection<Bookmark> list=BookmarkManager.getInstance().searchBooks(false,userId,name,1);
				request.setAttribute("books", list);
				}
				else {
					String rating=request.getParameter("Bname");
					Collection<Bookmark> list=BookmarkManager.getInstance().searchBooks(false,userId,rating,0);
					request.setAttribute("books", list);
				}
			}
			else if (request.getServletPath().contains("movie")) {
				dispatcher = request.getRequestDispatcher("/movie.jsp");

				Collection<Bookmark> list = BookmarkManager.getInstance().getMovies(false, userId);
				request.setAttribute("movies", list);

			} else {
				dispatcher = request.getRequestDispatcher("/browse.jsp");

				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(false, userId);
				request.setAttribute("books", list);

			}
		} else {
			dispatcher = request.getRequestDispatcher("/login.jsp");
		}
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().saveUserBookmark(user, bookmark);

	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		BookmarkManager.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);

	}

	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user, bookmark);

	}

}
