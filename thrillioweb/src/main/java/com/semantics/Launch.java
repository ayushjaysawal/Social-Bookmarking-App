package com.semantics;

import java.util.List;

import com.semantics.bgjobs.WebpageDownloaderTask;
import com.semantics.entities.Bookmark;
import com.semantics.entities.User;
import com.semantics.manager.BookmarkManager;
import com.semantics.manager.UserManager;

public class Launch {
	private static  List<User> users;
	private static List<List<Bookmark>> bookmarks;

	private static void loadData() {
		System.out.println("1.... Loading Data");
		DataStore.loadData();
		
		users=UserManager.getInstance().getUsers();
		bookmarks=BookmarkManager.getInstance().getBookmarks();
		
		//System.out.println("printing data.....");
		//printUserData();
		//printBookmarkData();
		
	}
	private static void printBookmarkData() {
		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				System.out.println(bookmark);
				
			}
			
		}
		
	}
	private static void printUserData() {
		for (User user : users ) {
			System.out.println(user);
		}
		
	}
	private static void start() {
		//System.out.println("\n2. Bookmarking......");
		for(User user:users) {
			View.browse(user, bookmarks);
		}
		
	}

	public static void main(String[] args) {
		loadData();
		start();
		
		//runDownloaderJob(); 
	}
	
	private static void runDownloaderJob() {
		WebpageDownloaderTask task=new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}

}
