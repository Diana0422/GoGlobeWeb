package logic.model.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class WhoIsAPI {

	public String getUserIP() {
		
		HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("http://ipwhois.app/json/"))
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
		
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				
			String logStr = response.body();
			Logger.getGlobal().info(logStr);
		        
		    JSONObject json = new JSONObject(response.body());
		    
		    logStr = "IP Address: "+json.getString("ip");
		    Logger.getGlobal().info(logStr);
		    return json.getString("ip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Logger.getGlobal().log(Level.WARNING, "Interrupted IP finding routine.", e);
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	       
		return null;
	}

}
