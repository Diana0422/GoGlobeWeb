package logic.model.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import logic.model.Location;
import logic.model.Place;
import logic.model.apis.HereImplementation;
import logic.model.factories.HereAdapterFactory;
import logic.model.interfaces.LocationFinder;

public class HereAPIAdapter implements LocationFinder{
	
	HashMap<String, String> categoryHash = new HashMap<>();
		
	private HereImplementation hereAPI;

	
	public HereAPIAdapter(HereImplementation api){
		this.hereAPI = api;
		
		categoryHash.put("ADVENTURE" ,  "&cat=natural-geographical");
		categoryHash.put("CULTURE"   , 	"&cat=sights-museums");
		categoryHash.put("FUN"       , 	"&cat=going-out");
		categoryHash.put("RELAX"     , 	"&cat=leisure-outdoor");
	}
	
	

	@Override
	public List<Place> getNearbyPlaces(String coordinates, String category) {	
		category = categoryHash.get(category.toUpperCase());		
		JSONObject response = hereAPI.getNearbyPlaces(coordinates, category);
		List<Place> nearbyPlaces = new ArrayList<>();
		JSONArray items = response.getJSONObject("results").getJSONArray("items");
		//parse every item in response to Place objects
		for (int i = 0; i < items.length(); i++) {
			
			String placeName;
			String placeAddress;
			String iconRef;
			String openingHours;
			
			JSONObject place = items.getJSONObject(i);			
			placeName = place.getString("title");
			System.out.println(placeName);
			placeAddress = place.getString("vicinity");
			System.out.println(placeAddress);
			iconRef = place.getString("icon");
			System.out.println(iconRef);
			try {
				openingHours = place.getJSONObject("openingHours").getString("text");
				System.out.println(openingHours);
			}catch(JSONException e){
				 openingHours = "";
			}
			

			Place newPlace = new Place(placeName, placeAddress, openingHours, iconRef);
			nearbyPlaces.add(newPlace);			
		}
		return nearbyPlaces;		
	}
	
	//Get location info
	@Override
	public Location getLocationInfo(String locationName) {
		JSONObject json = hereAPI.getLocationInfo(locationName);
		JSONArray items = json.getJSONArray("items");           
     	String countryName = items.getJSONObject(0).getJSONObject("address").getString("countryName");
     	String cityName = items.getJSONObject(0).getJSONObject("address").getString("city");
     	String lat = Double.toString(items.getJSONObject(0).getJSONObject("position").getDouble("lat"));
     	String lng = Double.toString(items.getJSONObject(0).getJSONObject("position").getDouble("lng"));
     	String cityCoordinates = lat + "," + lng;
		return new Location(cityName, countryName, cityCoordinates);
	}

}
