package logic.control;

import java.util.Date;
import java.util.Calendar;

import logic.dao.UserDAO;
import logic.dao.UserDAOFile;
import logic.model.Review;
import logic.model.RoleType;
import logic.model.User;
import logic.model.exceptions.SerializationException;
import logic.model.interfaces.Observer;

public class ReviewUserController {

	private static ReviewUserController instance = null;
	private static UserDAO dao;
	
	private ReviewUserController() {}
	
	public static ReviewUserController getInstance() {
		
		if (instance == null) {
			instance = new ReviewUserController();
			dao = new UserDAOFile();
		}
		return instance;
	}
	
	public boolean postReview(RoleType type, double rating, String comment, String title, String reviewerEmail, String targetEmail, Observer o) throws SerializationException {
		User reviewer = dao.getUser(reviewerEmail);
		User target = dao.getUser(targetEmail);
		target.getStats().attach(o);
		System.out.println(target);
		System.out.println(reviewer);
		
		Date today = Calendar.getInstance().getTime();
		
		Review review = new Review();
		review.setReviewer(reviewer);
		review.setType(type);
		review.setVote(rating);
		review.setDate(today);
		review.setComment(comment);
		review.setTitle(title);
		
		System.out.println("Review POSTED.");
		return target.addReview(review);
		
	}
}
