package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import logic.bean.UserBean;

public class UserItemGraphic implements GraphicController{
	
	@FXML
	private Label lblUserName;

	@FXML
	private Label lblAge;
	
	private UserBean user;
	
	private Object prevBundle;

	@FXML
	void displayProfile(MouseEvent event) {
		DesktopSessionContext.getGuiLoader().loadGUIStateful(user, prevBundle, GUIType.PROFILE, GUIType.INFO);
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

	@Override
	public void initializeData(Object recBundle, Object forBundle) {
		prevBundle = forBundle;
		setData((UserBean)recBundle);
	}

}
