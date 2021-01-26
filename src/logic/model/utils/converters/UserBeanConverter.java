package logic.model.utils.converters;

import java.util.List;

import logic.bean.UserBean;
import logic.model.User;
import logic.persistence.exceptions.DatabaseException;

public class UserBeanConverter implements BeanConverter<User,UserBean> {

	@Override
	public UserBean convertToBean(User o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User convertFromBean(UserBean o) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBean> convertToListBean(List<User> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> convertFromListBean(List<UserBean> list) {
		// TODO Auto-generated method stub
		return null;
	}



}
