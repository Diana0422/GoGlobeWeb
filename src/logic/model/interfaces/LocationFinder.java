package logic.model.interfaces;

import java.util.List;

import logic.model.Place;
import logic.model.Location;

public interface LocationFinder {
	
	
	public Location getLocationInfo(String locationName);
	
	
	public List<Place> getNearbyPlaces(String coordinates, String category);
}
