package logic.dao;

import java.util.List;

import logic.model.User;
import logic.model.exceptions.SerializationException;

public interface UserDAO {
	
	public List<User> getAllUsers() throws SerializationException;
	public User getUser(String email) throws SerializationException;
	public boolean updateUser(User newUser, String oldUserEmail) throws SerializationException;
	public boolean saveUser(User user) throws SerializationException;
}
