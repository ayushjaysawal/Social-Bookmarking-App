package com.semantics.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.semantics.DataStore;
import com.semantics.constant.BookGenre;
import com.semantics.constant.MovieGenre;
import com.semantics.entities.Book;
import com.semantics.entities.Bookmark;
import com.semantics.entities.Movie;
import com.semantics.entities.User;
import com.semantics.entities.UserBookmark;
import com.semantics.entities.WebLink;
import com.semantics.manager.BookmarkManager;

public class BookmarkDao {
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveUserBookmark(UserBookmark userBookmark) {
		// DataStore.add(userBookmark);
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			if (userBookmark.getBookmark() instanceof Book) {
				saveUserBook(userBookmark, stmt);
			} else if (userBookmark.getBookmark() instanceof Movie) {
				saveUserMovie(userBookmark, stmt);
			} else {
				saveUserWebLink(userBookmark, stmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void saveUserWebLink(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into User_WebLink (user_id,weblink_id) values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query);

	}

	private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into User_Movie (user_id,movie_id) values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query);

	}

	private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into User_Book (user_id,book_id) values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ")";
		stmt.executeUpdate(query);
	}

	public List<WebLink> getAllWebLinks() {
		List<WebLink> result = new ArrayList<>();
		List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
		List<Bookmark> allWebLinks = bookmarks.get(0);

		for (Bookmark bookmark : allWebLinks) {
			result.add((WebLink) bookmark);
		}
		return result;
	}

	public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
		List<WebLink> result = new ArrayList<>();

		List<WebLink> allWebLinks = getAllWebLinks();
		for (WebLink weblink : allWebLinks) {
			if (weblink.getDownloadStatus().equals(downloadStatus)) {
				result.add(weblink);
			}
		}

		return result;
	}

	public void updateKidFriendlyStatus(Bookmark bookmark) {
		int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
		long userId = bookmark.getKidFriendlyMarkedBy().getId();

		String tableToUpdate = "Book";
		if (bookmark instanceof Movie) {
			tableToUpdate = "Movie";
		} else if (bookmark instanceof WebLink) {
			tableToUpdate = "WebLink";
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "update " + tableToUpdate + " set kid_friendly_status = " + kidFriendlyStatus
					+ " , kid_friendly_marked_by = " + userId + " where id = " + bookmark.getId();
			System.out.println("query (updateKidFriendlyStatus): " + query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sharedByInfo(Bookmark bookmark) {
		long userId = bookmark.getSharedBy().getId();

		String tableToUpdate = "Book";
		if (bookmark instanceof WebLink) {
			tableToUpdate = "WebLink";
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "update " + tableToUpdate + " set shared_by = " + userId + " where id = " + bookmark.getId();
			System.out.println("query (update sharedbyStatus): " + query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// new com.mysql.jdbc.Driver();
			// OR
			// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

			// OR java.sql.DriverManager
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "";
			String kid = "Select kid_Friendly from user where id = " + userId;
			ResultSet kd = stmt.executeQuery(kid);
			int kid_Friendly = 0;
			while (kd.next()) {
				kid_Friendly = kd.getInt("kid_Friendly");
			}
			if (!isBookmarked) {
				if (kid_Friendly == 0) {
					query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
							+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and "
							+ "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
							+ " and u.id = ub.user_id) group by b.id";
				} else {
					query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
							+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and b.kid_friendly_status <> "
							+ 1 + " and " + "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = "
							+ userId + " and u.id = ub.user_id) group by b.id";
				}
			} else {
				query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
						+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and "
						+ "b.id IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
						+ " and u.id = ub.user_id) group by b.id";
			}

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String imageUrl = rs.getString("image_url");
				int publicationYear = rs.getInt("publication_year");
				// String publisher = rs.getString("name");
				String[] authors = rs.getString("authors").split(",");
				int genre_id = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");

				System.out.println(
						"id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", authors: "
								+ String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, null,
						authors, genre, amazonRating/* , values[7] */);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Collection<Bookmark> getBooks(long userId) {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// new com.mysql.jdbc.Driver();
			// OR
			// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

			// OR java.sql.DriverManager
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
					+ "amazon_rating,sbb.sender_id from Book b, Author a, Book_Author ba,share_book sbb where b.id = ba.book_id and ba.author_id = a.id and "
					+ "b.id IN (select sb.bid from share_book sb where sb.reciever_id =" + userId + ")"
					+ " group by b.id";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String imageUrl = rs.getString("image_url");
				int publicationYear = rs.getInt("publication_year");
				// String publisher = rs.getString("name");
				String[] authors = rs.getString("authors").split(",");
				int genre_id = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");
				String senderEmail = getEmail(rs.getInt("sender_id"));

				System.out.println(
						"id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", authors: "
								+ String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, null,
						authors, genre, amazonRating, senderEmail);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	public Collection<Bookmark> getUserBooks(long userId) {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "Select b.id,b.title,b.author,b.publicationYear from ubook b,user_ubook ub where b.id=ub.bid and ub.uid = "+userId;

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				
				int publicationYear = rs.getInt("publicationYear");
				
				String[] authors = rs.getString("author").split(",");
				
				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title,publicationYear,authors);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public Collection<Bookmark> getUserMovies(long userId) {
		Collection<Bookmark> result = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "Select m.id,m.title,m.director,m.releaseYear from umovie m,user_umovie um where m.id=um.mid and um.uid = "+userId;

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				
				int releaseYear = rs.getInt("releaseYear");
				
				String[] directors = rs.getString("director").split(",");
				
				Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title,releaseYear,directors);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public Collection<Bookmark> getAllUserBooks() {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "Select b.id,b.title,b.author,b.publicationYear from ubook b";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				
				int publicationYear = rs.getInt("publicationYear");
				
				String[] authors = rs.getString("author").split(",");
				
				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title,publicationYear,authors);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Collection<Bookmark> getAllUserMovies() {
		Collection<Bookmark> result = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "Select m.id,m.title,m.director,m.releaseYear from umovie m";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				
				int releaseYear = rs.getInt("releaseYear");
				
				String[] directors = rs.getString("director").split(",");
				
				Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title,releaseYear,directors);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	private String getEmail(int senderId) {
		String email = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "Select email from user where id=" + senderId;

			System.out.println("query (update sharedbyStatus): " + query);
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				email = rs.getString("email");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return email;
	}

	public Bookmark getBook(long bookId) {
		Book book = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "Select b.id, title, image_url, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
					+ " from Book b, Publisher p, Author a, Book_Author ba " + "where b.id = " + bookId
					+ " and b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String imageUrl = rs.getString("image_url");
				int publicationYear = rs.getInt("publication_year");
				String publisher = rs.getString("name");
				String[] authors = rs.getString("authors").split(",");
				int genre_id = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");

				System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear
						+ ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre
						+ ", amazonRating: " + amazonRating);

				book = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, publisher,
						authors, genre, amazonRating/* , values[7] */);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	public Movie getMovie(long movieId) {
		Movie movie = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {
			String query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
					+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md " + "where m.id= "
					+ movieId + " and m.id = ma.movie_id and ma.actor_id = a.id and "
					+ "m.id = md.movie_id and md.director_id = d.id group by m.id";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				int releaseYear = rs.getInt("release_year");
				String[] cast = rs.getString("cast").split(",");
				String[] directors = rs.getString("directors").split(",");
				int genre_id = rs.getInt("movie_genre_id");
				MovieGenre genre = MovieGenre.values()[genre_id];
				double imdbRating = rs.getDouble("imdb_rating");

				movie = BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast, directors, genre,
						imdbRating);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}

	public Collection<Bookmark> getMovies(boolean isBookmarked, long userId) {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// new com.mysql.jdbc.Driver();
			// OR
			// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

			// OR java.sql.DriverManager
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "";
			String kid = "Select kid_Friendly from user where id = " + userId;
			ResultSet kd = stmt.executeQuery(kid);
			int kid_Friendly = 0;
			while (kd.next()) {
				kid_Friendly = kd.getInt("kid_Friendly");
			}
			if (!isBookmarked) {
				if (kid_Friendly == 0) {
					query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
							+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
							+ "where m.id = ma.movie_id and ma.actor_id = a.id and "
							+ "m.id = md.movie_id and md.director_id = d.id and "
							+ "m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
							+ " and u.id = um.user_id) group by m.id";
				} else {
					query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
							+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
							+ "where m.id = ma.movie_id and ma.actor_id = a.id and m.kid_friendly_status <> " + 1
							+ " and " + "m.id = md.movie_id and md.director_id = d.id and "
							+ "m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
							+ " and u.id = um.user_id) group by m.id";
				}
			} else {
				query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
						+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
						+ "where m.id = ma.movie_id and ma.actor_id = a.id and "
						+ "m.id = md.movie_id and md.director_id = d.id and "
						+ "m.id  IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
						+ " and u.id = um.user_id) group by m.id";
			}

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				int releaseYear = rs.getInt("release_year");
				String[] cast = rs.getString("cast").split(",");
				String[] directors = rs.getString("directors").split(",");
				int genre_id = rs.getInt("movie_genre_id");
				MovieGenre genre = MovieGenre.values()[genre_id];
				double imdbRating = rs.getDouble("imdb_rating");

				Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast,
						directors, genre, imdbRating);

				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void removeBook(long bookId, long userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "Delete from user_book where user_id = " + userId + " and book_id = " + bookId;

			System.out.println("query (update sharedbyStatus): " + query);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void removeUserBook(long bid, long userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "Delete from user_ubook where uid = " + userId + " and bid = " + bid;

			System.out.println("query (update sharedbyStatus): " + query);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeMovie(long movieId, long userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "Delete from user_movie where user_id = " + userId + " and movie_id = " + movieId;

			System.out.println("query (update sharedbyStatus): " + query);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void removeUserMovie(long mid, long userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "Delete from user_umovie where uid = " + userId + " and mid = " + mid;

			System.out.println("query (update sharedbyStatus): " + query);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	
	public Collection<Bookmark> searchMovies(boolean isBookmarked, long userId, String name, int i) {
		Collection<Bookmark> result = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// new com.mysql.jdbc.Driver();
			// OR
			// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

			// OR java.sql.DriverManager
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "";
			String kid = "Select kid_Friendly from user where id = " + userId;
			ResultSet kd = stmt.executeQuery(kid);
			int kid_Friendly = 0;
			while (kd.next()) {
				kid_Friendly = kd.getInt("kid_Friendly");
			}
			if (i == 1) {
				if (kid_Friendly == 0) {
					query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
							+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
							+ "where m.id = ma.movie_id and ma.actor_id = a.id and "
							+ "m.id = md.movie_id and md.director_id = d.id and m.title like '" + name + "%' and "
							+ "m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
							+ " and u.id = um.user_id) group by m.id";
				} else {
					query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
							+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
							+ "where m.id = ma.movie_id and ma.actor_id = a.id and m.kid_friendly_status <> " + 1
							+ " and m.title like '" + name + "%' and "
							+ "m.id = md.movie_id and md.director_id = d.id and "
							+ "m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
							+ " and u.id = um.user_id) group by m.id";
				}
			} else {
				Double rating = Double.parseDouble(name);
				if (kid_Friendly == 0) {

					query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
							+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
							+ "where m.id = ma.movie_id and ma.actor_id = a.id and m.imdb_rating >=" + rating + " and "
							+ "m.id = md.movie_id and md.director_id = d.id and "
							+ "m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
							+ " and u.id = um.user_id) group by m.id";
				} else {
					query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
							+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
							+ "where m.id = ma.movie_id and ma.actor_id = a.id and m.kid_friendly_status <> " + 1
							+ " and m.imdb_rating >=" + rating + "  and "
							+ "m.id = md.movie_id and md.director_id = d.id and "
							+ "m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
							+ " and u.id = um.user_id) group by m.id";
				}
			}

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				int releaseYear = rs.getInt("release_year");
				String[] cast = rs.getString("cast").split(",");
				String[] directors = rs.getString("directors").split(",");
				int genre_id = rs.getInt("movie_genre_id");
				MovieGenre genre = MovieGenre.values()[genre_id];
				double imdbRating = rs.getDouble("imdb_rating");

				Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast,
						directors, genre, imdbRating);

				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Collection<Bookmark> searchBooks(boolean isBookmarked, long userId, String name, int i) {
		Collection<Bookmark> result = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// new com.mysql.jdbc.Driver();
			// OR
			// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

			// OR java.sql.DriverManager
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false",
				"root", "password"); Statement stmt = conn.createStatement();) {

			String query = "";
			String kid = "Select kid_Friendly from user where id = " + userId;
			ResultSet kd = stmt.executeQuery(kid);
			int kid_Friendly = 0;
			while (kd.next()) {
				kid_Friendly = kd.getInt("kid_Friendly");
			}
			if (i == 1) {
				if (kid_Friendly == 0) {
					query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
							+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and b.title like '"
							+ name + "%' and "
							+ "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
							+ " and u.id = ub.user_id) group by b.id";
				} else {
					query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
							+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and b.kid_friendly_status <> "
							+ 1 + " and b.title like '" + name
							+ "%' and b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
							+ " and u.id = ub.user_id) group by b.id";
				}
			} else {
				Double rating = Double.parseDouble(name);
				if (kid_Friendly == 0) {
					query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
							+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and b.amazon_rating >="
							+ rating + " and "
							+ "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
							+ " and u.id = ub.user_id) group by b.id";
				} else {
					query = "Select b.id, title, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
							+ "amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and b.kid_friendly_status <> "
							+ 1 + " and b.amazon_rating >=" + rating
							+ " and b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
							+ " and u.id = ub.user_id) group by b.id";
				}
			}

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String imageUrl = rs.getString("image_url");
				int publicationYear = rs.getInt("publication_year");
				// String publisher = rs.getString("name");
				String[] authors = rs.getString("authors").split(",");
				int genre_id = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");

				System.out.println(
						"id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", authors: "
								+ String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

				Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, null,
						authors, genre, amazonRating/* , values[7] */);
				result.add(bookmark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean addBooks(String title, String image, int publishYear, String publisher, String author,
			int book_Genre, int kid_Friendly, double rating) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			long publisherId=getAuthorId(publisher,"publisher");
			String query = "Insert into Book(title,image_url,publication_year,publisher_id,book_genre_id,amazon_rating,kid_friendly_status,created_date)"
					+ " values ('"+title+"','"+image+"',"+publishYear+","+publisherId+","+book_Genre+","+rating+","+kid_Friendly+","
							+ "NOW() )";

			stmt.executeUpdate(query);
			query="Select id from Book where title ='"+title+"' and publication_year ="+publishYear;
			ResultSet rs=stmt.executeQuery(query);
			
			long bookId=-1;
			while(rs.next()) {
				bookId=rs.getLong("id");
			}
			long authorId=getAuthorId(author,"author");
			query="Insert into book_author(book_id,author_id) values ("+bookId+","+authorId+")";
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private long getAuthorId(String publisher, String tableName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			String query = "select id from "+tableName+" where name = '"+publisher+"'";

			ResultSet rs=stmt.executeQuery(query);
			long id=-1;
			while(rs.next()) {
				id=rs.getLong("id");
			}
			if(id!=-1) {
				return id;
			}
			else {
				query="insert into "+tableName+" (name) values ('"+publisher+"')";
				stmt.executeUpdate(query);
				query = "select id from "+tableName+" where name = '"+publisher+"'";

				 rs=stmt.executeQuery(query);
				
				while(rs.next()) {
					id=rs.getLong("id");
				}
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public boolean addMovies(String title, String image, int release_Year, String[] actor, String[] director,
			int movie_Genre, int kid_Friendly, double rating) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			
			String query = "Insert into Movie(title,release_year,movie_genre_id,imdb_rating,kid_friendly_status,created_date)"
					+ " values ('"+title+"',"+release_Year+","+movie_Genre+","+rating+","+kid_Friendly+","
							+ "NOW() )";

			stmt.executeUpdate(query);
			query="Select id from Movie where title ='"+title+"' and release_year ="+release_Year;
			ResultSet rs=stmt.executeQuery(query);
			
			long movieId=-1;
			while(rs.next()) {
				movieId=rs.getLong("id");
			}
			int n=actor.length;
			int i=0;
			while(i<n) {
				long actorId=getAuthorId(actor[i],"actor");
				query="Insert into movie_actor(movie_id,actor_id) values ("+movieId+","+actorId+")";
				stmt.executeUpdate(query);
				i++;
			}
			n=director.length;
			i=0;
			while(i<n) {
				long directorId=getAuthorId(director[i],"director");
				query="Insert into movie_director(movie_id,director_id) values ("+movieId+","+directorId+")";
				stmt.executeUpdate(query);
				i++;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean addUserBooks(String title, String publicationYear, String authors, long userId) {
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
					"password"); Statement stmt = conn.createStatement();) {
				
				 String query="Select id from uBook where title ='"+title+"' and publicationYear ="+publicationYear;
				ResultSet rs=stmt.executeQuery(query);
				long bid=-1;
				while(rs.next()) bid=rs.getLong("id");
				if(bid==-1) {
				 query = "Insert into ubook(title,publicationYear,author)"
						+ " values ('"+title+"',"+publicationYear+",'"+authors+"')";

				stmt.executeUpdate(query);
				query="Select id from uBook where title ='"+title+"' and publicationYear ="+publicationYear;
				rs=stmt.executeQuery(query);
				
				while(rs.next()) bid=rs.getLong("id");
				}
				query="Insert into user_ubook(bid,uid) values ("+bid+","+userId+")";
				stmt.executeUpdate(query);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return false;
	}

	public boolean addUserMovies(String title, String releaseYear, String directors, long userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root",
				"password"); Statement stmt = conn.createStatement();) {
			
			String query="Select id from umovie where title ='"+title+"' and releaseYear ="+releaseYear;
			ResultSet rs=stmt.executeQuery(query);
			long mid=-1;
			while(rs.next()) mid=rs.getLong("id");
			if(mid==-1) {
			 query = "Insert into umovie(title,releaseYear,director)"
					+ " values ('"+title+"',"+releaseYear+",'"+directors+"')";

			stmt.executeUpdate(query);
			query="Select id from umovie where title ='"+title+"' and releaseYear ="+releaseYear;
			 rs=stmt.executeQuery(query);
			
			while(rs.next()) mid=rs.getLong("id");
			}
			query="Insert into user_umovie(mid,uid) values ("+mid+","+userId+")";
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	
	
	

}
