package com.semantics;

import java.util.List;

import com.semantics.constant.KidFriendlyStatus;
import com.semantics.constant.UserType;
import com.semantics.controllers.BookmarkController;
import com.semantics.entities.Bookmark;
import com.semantics.entities.User;
import com.semantics.partner.Shareable;

public class View {
	public static void browse(User user, List<List<Bookmark>> bookmarks) {
		System.out.println("\n" + user.getEmail() + "is browsing item.....");
		int bookmarkCount = 0;
		
		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				// Bookmarking!!
				
					boolean isBookmarked = getBookmarkDecision(bookmark);
					if (isBookmarked) {
						bookmarkCount++;

						BookmarkController.getInstance().saveUserBookmark(user, bookmark);

						System.out.println("New Item Bookmarked...." + bookmark);
					}
				
					
				if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEFEDITOR)) {
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						KidFriendlyStatus kidFriendlyStatus=getKidFriendlyStatusDecision(bookmark);
						if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus,bookmark);
							
						}
					}
					// sharing !!
					if(bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) && bookmark instanceof Shareable){
						boolean isShared=getShareDecision();
						if(isShared) {
							BookmarkController.getInstance().share(user,bookmark);
						}
						
					}

				}
			}
		}

			

		}
		

	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true : false;
		
	}


	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
	    double randomVal = Math.random();
	     
	    return randomVal < 0.4 ? KidFriendlyStatus.APPROVED:
	        (randomVal >= 0.4 && randomVal < 0.8) ? KidFriendlyStatus.REJECTED:
	            KidFriendlyStatus.UNKNOWN;
	 
	    
	}
	

	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;

	}

}
