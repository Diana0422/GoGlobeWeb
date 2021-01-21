package logic.control;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

import logic.persistence.dao.ReviewDao;
import logic.persistence.dao.UserDaoDB;
import logic.persistence.dao.UserStatsDao;
import logic.bean.ReviewBean;
import logic.bean.UserBean;
import logic.model.Review;
import logic.model.RoleType;
import logic.model.User;
import logic.model.exceptions.SerializationException;
import logic.model.interfaces.Observer;

public class ReviewUserController {

	private static ReviewUserController instance = null;
	
	private ReviewUserController() {}
	
	public static ReviewUserController getInstance() {
		
		if (instance == null) {
			instance = new ReviewUserController();
		}
		return instance;
	}
	
	public boolean postReview(String type, double rating, String comment, String title, String reviewerEmail, String targetEmail, Observer o) throws SerializationException {
		User reviewer = UserDaoDB.getInstance().get(reviewerEmail);
		User target = UserDaoDB.getInstance().get(targetEmail);
		target.setStats(UserStatsDao.getInstance().getUserStats(targetEmail));
		if (o != null) {
			target.getStats().attach(o);
		}
		System.out.println(target);
		System.out.println(reviewer);
		
		Date today = Calendar.getInstance().getTime();
		
		Review review = new Review();
		review.setReviewer(reviewer);
		if (type.equalsIgnoreCase("traveler")) {
			review.setType(RoleType.TRAVELER);
		} else {
			review.setType(RoleType.ORGANIZER);
		}
		review.setVote(rating);
		review.setDate(today);
		review.setComment(comment);
		review.setTitle(title);
		
		System.out.println("Review POSTED.");
		target.addReview(review);
		return ReviewDao.getInstance().save(review, target.getEmail());
		
	}
	
	
	public List<ReviewBean> getUserReviews(UserBean user) {
		List<Review> list = ReviewDao.getInstance().getUserReviews(user.getEmail());
		User target = UserDaoDB.getInstance().get(user.getEmail());
		// Complete the missing reviewer information
		for (Review r: list) {
			User reviewer = UserDaoDB.getInstance().get(r.getReviewer().getEmail());
			r.setReviewer(reviewer);
			target.addReview(r);
		}
		
		List<Review> reviews = target.getReviews();
		return ConversionController.getInstance().convertReviewList(reviews);
	}
}
