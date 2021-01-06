package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import logic.bean.UserBean;

public class UserItemGraphic {
	
	@FXML
	private Label lblUserName;

	@FXML
	private Label lblAge;
	
	private UserBean user;

	@FXML
	void displayProfile(MouseEvent event) {
		UpperNavbarControl.getInstance().loadProfile(getUser());
	}

	public void setData(UserBean bean) {
		setUser(bean);
		lblUserName.setText(bean.getName()+" "+bean.getSurname());
		lblAge.setText(Integer.toString(bean.getAge()));
		
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

}
