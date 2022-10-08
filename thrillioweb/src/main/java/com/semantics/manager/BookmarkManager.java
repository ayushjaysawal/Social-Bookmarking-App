package com.semantics.manager;

import java.util.Collection;
import java.util.List;

import com.semantics.constant.BookGenre;
import com.semantics.constant.KidFriendlyStatus;
import com.semantics.constant.MovieGenre;
import com.semantics.dao.BookmarkDao;
import com.semantics.entities.Book;
import com.semantics.entities.Bookmark;
import com.semantics.entities.Movie;
import com.semantics.entities.User;
import com.semantics.entities.UserBookmark;
import com.semantics.entities.WebLink;


public class BookmarkManager {
	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {
	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public WebLink createWebLink(long id, String title, String url, String host) {
		WebLink weblink = new WebLink();
		weblink.setId(id);
		weblink.setTitle(title);
		weblink.setUrl(url);
		weblink.setHost(host);

		return weblink;
	}

	public Book createBook(long id, String title,String imageUrl, int publicationYear, String publisher, String[] authors,BookGenre  genre,
			double amazonRating) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setImageUrl(imageUrl);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);

		return book;
	}
	public Book createBook(long id, String title,String imageUrl, int publicationYear, String publisher, String[] authors,BookGenre  genre,
			double amazonRating,String senderEmail) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setImageUrl(imageUrl);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);
		book.setSenderEmail(senderEmail);

		return book;
	}

	public Bookmark createBook(long id, String title, int publicationYear, String[] authors) {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setPublicationYear(publicationYear);
		book.setAuthors(authors);
		return book;
	}
	
	public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenre genre, double imdbRating) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseyear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);

		return movie;

	}

	public Bookmark createMovie(long id, String title, int releaseYear, String[] directors) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);		
		movie.setReleaseyear(releaseYear);		
		movie.setDirectors(directors);
		return movie;
	}
	
	public List<List<Bookmark>> getBookmarks() {
		return dao.getBookmarks();
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);
		
		
		
		dao.saveUserBookmark(userBookmark);
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		bookmark.setKidFriendlyStatus(kidFriendlyStatus);
		bookmark.setKidFriendlyMarkedBy(user);
		
		dao.updateKidFriendlyStatus(bookmark);

		System.out.println(
				"Kid-friendly status: " + kidFriendlyStatus + ", Marked by: " + user.getEmail() + ", " + bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		bookmark.setSharedBy(user);
		
		System.out.println("Data to be shared: ");
		if (bookmark instanceof Book) {
			System.out.println(((Book)bookmark).getItemData());
		} else if (bookmark instanceof WebLink) {
			System.out.println(((WebLink)bookmark).getItemData());
		}
		
		dao.sharedByInfo(bookmark);
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		
		return dao.getBooks(isBookmarked,userId);
		
	}

	public Bookmark getBook(long bid) {
		return dao.getBook(bid);
	}

	public Bookmark getMovie(long mid) {
		
		return dao.getMovie(mid);
	}

	public Collection<Bookmark> getMovies(boolean isBookmarked, long userId) {
		return dao.getMovies(isBookmarked,userId);	
	}

	public void removeBook(long bid,long userId) {
		// TODO Auto-generated method stub
		 dao.removeBook(bid,userId);
	}

	public void removeMovie(long mid, long userId) {
		// TODO Auto-generated method stub
		dao.removeMovie(mid,userId);
	}

	public Collection<Bookmark> searchBooks(boolean isBookmarked, long userId, String name,int i) {
		// TODO Auto-generated method stub
		return dao.searchBooks(isBookmarked,userId,name,i);
	}

	public Collection<Bookmark> searchMovies(boolean b, long userId, String name, int i) {
		
		return dao.searchMovies(b, userId, name, i);
	}

	public boolean addBooks(String title, String image, String publicationYear, String publisher, String author,
			String bookGenre, String kidFriendly, String bookRating) {
		// TODO Auto-generated method stub
		int publishYear=Integer.parseInt(publicationYear);
		double rating=Double.parseDouble(bookRating);
		int kid_Friendly=KidFriendlyStatus.valueOf(kidFriendly.toUpperCase()).ordinal();
		int book_Genre=BookGenre.valueOf(bookGenre.toUpperCase()).ordinal();
		kid_Friendly=2-kid_Friendly;
		if(dao.addBooks(title,image,publishYear,publisher,author,book_Genre,kid_Friendly,rating)) return true;
		return false;
	}

	public boolean addMovies(String title, String image, String releaseYear, String actors, String directors,
			String movieGenre, String kidFriendly, String movieRating) {
		// TODO Auto-generated method stub
		int release_Year=Integer.parseInt(releaseYear);
		String actor[]=actors.split(",");
		String director[]=directors.split(",");
		double rating=Double.parseDouble(movieRating);
		int kid_Friendly=2-KidFriendlyStatus.valueOf(kidFriendly.toUpperCase()).ordinal();
		int movie_Genre=MovieGenre.valueOf(movieGenre.toUpperCase()).ordinal();
		if(dao.addMovies(title,image,release_Year,actor,director,movie_Genre,kid_Friendly,rating)) return true;
		return false;
	}

	public boolean addUserBooks(String title, String publicationYear, String authors, long userId) {
		// TODO Auto-generated method stub
		if(dao.addUserBooks(title,publicationYear,authors,userId)) return true;
		return false;
	}

	

	public Collection<Bookmark> getUserBooks(long userId) {
		// TODO Auto-generated method stub
		return dao.getUserBooks(userId);
		
	}

	public boolean addUserMovies(String title, String releaseYear, String directors, long userId) {
		if(dao.addUserMovies(title,releaseYear,directors,userId)) return true;
		return false;
	}

	public Collection<Bookmark> getUserMovies(long userId) {
		return dao.getUserMovies(userId);
	}

	public Collection<Bookmark> getAllUserBooks() {
		// TODO Auto-generated method stub
		return dao.getAllUserBooks();
	}

	public Collection<Bookmark> getAllUserMovies() {
		// TODO Auto-generated method stub
		return dao.getAllUserMovies();
	}

	public void removeUserBook(long bid, long userId) {
		dao.removeUserBook(bid,userId);
	}

	public void removeUserMovie(long mid, long userId) {
		dao.removeUserMovie(mid,userId);
	}

	

	
}
