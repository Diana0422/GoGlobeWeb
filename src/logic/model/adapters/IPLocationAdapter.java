package logic.model.adapters;

import logic.model.apis.IPFindAPI;
import logic.model.interfaces.IPCurrentPosition;

public class IPLocationAdapter implements IPCurrentPosition {
	
	private IPFindAPI ipLocation;
	
	public IPLocationAdapter(IPFindAPI ipLocation) {
		this.ipLocation = ipLocation;
	}

	@Override
	public String getIPCurrentPosition(String ipAddress) {
		return ipLocation.getUserLatAndLong(ipAddress);
	}

}
