package logic.model.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import logic.model.interfaces.IPLocationAPI;

public class IPFindImplementation implements IPLocationAPI {
	
	private static final String APIKEY = "534948a4-7cc6-4cd6-b9ed-be250ff572f4";

	@Override
	public String getUserLatAndLong(String ip) {
		HttpRequest request2 = HttpRequest.newBuilder()
    			.uri(URI.create("https://api.ipfind.com?ip="+ip+"&auth="+APIKEY))
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
        
        HttpResponse<String> response2;
		try {
			response2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
				
			System.out.println("\n"+response2.body());
		    	
		    JSONObject ipInfo = new JSONObject(response2.body());
		    return ipInfo.getString("city");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Logger.getGlobal().log(Level.WARNING, "Interrupted IP location routine.", e);
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	
		return null;
	}

}
