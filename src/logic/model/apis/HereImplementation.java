package logic.model.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;


public class HereImplementation {
	
	
	private static final String apiKey = "oDbN2CPeuk1ebsCIJhhcaxEKnKuGCJiUVQB9KYkjAYY";
	
	
	public JSONObject getNearbyPlaces(String coordinates, String category) {
		try
        {
        	HttpRequest request = HttpRequest.newBuilder()
        			.uri(URI.create("https://places.ls.hereapi.com/places/v1/discover/explore"
        					+ "?apiKey="+ apiKey
        					+ "&in=" + coordinates + ";r=80000" // r=80000 for category Adventure, Relax; r=15000 other
//        					+ "&cat=natural-geographical" // --> for category Adventure
//        					+ "&cat=sights-museums" // --> for category Culture
//        					+ "&cat=leisure-outdoor" // --> for category Relax
//        					+ "&cat=going-out" --> for category Fun
        					+ category
        					+ "&size=50"
        					+ "&pretty"))
        			.method("GET", HttpRequest.BodyPublishers.noBody())
        			.header("Accepted-Language", "en-en")
        			.build();
 
        
        	HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        	System.out.println(request.toString());
        	
        	return new JSONObject(response.body());
        }
        catch (IOException | JSONException ex)
        {
            ex.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
        	Thread.currentThread().interrupt();
			e.printStackTrace();
		}	
		return null;
    }

	
	//GET LOCATION'S GEOCODE INFORMATION	
	public JSONObject getLocationInfo(String locationName) {				
           return getGeocodeResponse(locationName);            	
    }
	
	//SEND GEOCODE REQUEST FOR locationName
	public JSONObject getGeocodeResponse(String locationName) {
		try
        {
			//Generating request
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://geocode.search.hereapi.com/v1/"
							+"geocode" 
							+"?q=" + locationName
							+"&apiKey=" + apiKey))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();       
        	HttpResponse<String> response = HttpClient.newHttpClient()
        			.send(request, HttpResponse.BodyHandlers.ofString());
        	
            return new JSONObject(response.body());
	}catch (IOException | JSONException ex)
        {
        ex.printStackTrace();
    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
    	Thread.currentThread().interrupt();
		e.printStackTrace();
	}		
		return new JSONObject();
	}	
	
}
