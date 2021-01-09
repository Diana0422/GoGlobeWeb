package logic.model.adapters;

import logic.model.apis.WhoIsAPI;
import logic.model.interfaces.CurrentIPFinder;

public class IPFinderAdapter implements CurrentIPFinder {
	
	private WhoIsAPI ipFinderApi;
	
	public IPFinderAdapter(WhoIsAPI ipfind) {
		this.ipFinderApi = ipfind;
	}

	@Override
	public String getCurrentIP() {
		return ipFinderApi.getUserIP();
	}

}
