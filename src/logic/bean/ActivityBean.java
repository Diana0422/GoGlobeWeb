package logic.bean;

import java.util.logging.Logger;

public class ActivityBean {
	
	private String time;
	private String title;
	private String description;
	
	
	public ActivityBean() {
		//Constructor of a bean must be empty 
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean validateActivity() {
		String infoStr = "TITLE CHECK:" + validateTitle(this.title);
		Logger.getGlobal().info(infoStr);
		infoStr = "TIME CHECK:" + validateTime(this.time);
		Logger.getGlobal().info(infoStr);
		infoStr = "DESCRIPTION CHECK:" + validateDescription(this.description);
		Logger.getGlobal().info(infoStr);
		
		return (validateTitle(this.title) && validateTime(this.time) && validateDescription(this.description));
	}
	
	private boolean validateTitle(String title) {
		return (!(title == null || title.equals("")  ));
		
	}
	
	
	private boolean validateDescription(String description) {
		
		return(!(description == null || description.equals("") ));
	}
	
	private boolean validateTime (String time) {
		if (time == null || time.equals("") || time.length() < 5) return false;
		
		try {
			int hour = Integer.parseInt(time.substring(0, 2));
			int minute = Integer.parseInt(time.substring(3));
			return (validateHour(hour) && validateMinute(minute));
		}catch(NumberFormatException e) {
			return false;
		}	
		
	}
	
	private boolean validateHour(int hour) {
		return ((hour >= 0) && (hour < 24));
	}
	
	private boolean validateMinute(int minute) {
		return ((minute >= 0) && (minute < 60));
	}

}
