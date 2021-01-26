package logic.model.utils.converters;

import java.util.ArrayList;
import java.util.List;

import logic.bean.ReviewBean;
import logic.control.ConversionController;
import logic.model.Review;
import logic.persistence.exceptions.DatabaseException;

public class ReviewBeanConverter implements BeanConverter<Review,ReviewBean> {

	@Override
	public ReviewBean convertToBean(Review o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Review convertFromBean(ReviewBean o) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> convertToListBean(List<Review> list) {
		List<ReviewBean> listBean = new ArrayList<>();
		if (list.isEmpty()) return listBean;
		for (Review rev: list) {
			ReviewBean bean = new ReviewBean();
			bean.setReviewerName(rev.getReviewer().getName());
			bean.setReviewerSurname(rev.getReviewer().getSurname());
			bean.setTitle(rev.getTitle());
			bean.setComment(rev.getComment());
	    	String date = ConversionController.getInstance().formatDate(rev.getDate());
	    	bean.setDate(date);
	    	bean.setVote(rev.getVote());
	    	listBean.add(bean);
		}
		
		return listBean;
	}

	@Override
	public List<Review> convertFromListBean(List<ReviewBean> list) {
		// TODO Auto-generated method stub
		return null;
	}


}
