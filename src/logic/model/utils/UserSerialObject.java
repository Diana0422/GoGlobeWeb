package logic.model.utils;
import java.io.Serializable;
import java.util.List;

import logic.model.User;

public class UserSerialObject implements Serializable {

	private static final long serialVersionUID = -1136910206204704953L;
	private List<User> list;
	
	public UserSerialObject(List<User> list) {
		this.setList(list);
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

}
