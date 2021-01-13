package logic.model;

public class Location {
	
	private String name;
	private String city;
	private String country;
	private String coordinates;
	
	public Location(String name, String country, String coordinates){
        this.name = name;
        this.country = country;
        this.setCoordinates(coordinates);

    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
}
