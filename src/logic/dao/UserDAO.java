package logic.dao;

import java.util.List;

import logic.model.User;

public interface UserDAO {
	
	public List<User> getAllUsers();
	public User getUser(String email);
	public boolean updateUser(User newUser, User oldUser);
	public boolean saveUser(User user);
}