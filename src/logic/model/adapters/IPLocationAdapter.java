package logic.model.adapters;

import logic.model.interfaces.IPCurrentPosition;
import logic.model.interfaces.IPLocationAPI;

public class IPLocationAdapter implements IPCurrentPosition {
	
	private IPLocationAPI ipLocation;
	
	public IPLocationAdapter(IPLocationAPI ipLocation) {
		this.ipLocation = ipLocation;
	}

	@Override
	public String getIPCurrentPosition(String ipAddress) {
		return ipLocation.getUserLatAndLong(ipAddress);
	}

}
