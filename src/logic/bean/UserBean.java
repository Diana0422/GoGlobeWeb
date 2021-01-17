package logic.bean;

import java.util.List;

public class UserBean {
	private String email;
	private String name;
	private String surname;
	private int age;
	private int points;
	private String bio;
	private double orgRating;
	private double travRating;
	private List<ReviewBean> reviews;
	
	public UserBean() {
		//empty constructor
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<ReviewBean> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewBean> reviews) {
		this.reviews = reviews;
	}

	public double getOrgRating() {
		return orgRating;
	}

	public void setOrgRating(double orgRating) {
		this.orgRating = orgRating;
	}

	public double getTravRating() {
		return travRating;
	}

	public void setTravRating(double travRating) {
		this.travRating = travRating;
	}

}
