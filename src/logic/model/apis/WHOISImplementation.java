package logic.model.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import logic.model.interfaces.IPFinderAPI;

public class WHOISImplementation implements IPFinderAPI {

	@Override
	public String getUserIP() {
		
		HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("http://ipwhois.app/json/"))
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
		
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			
			System.out.println(response.body());
	        
	        JSONObject json = new JSONObject(response.body());
	        System.out.println("IP Address: "+json.getString("ip"));
	        return json.getString("ip");
	        
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
