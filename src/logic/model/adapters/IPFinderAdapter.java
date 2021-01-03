package logic.model.adapters;

import logic.model.interfaces.CurrentIPFinder;
import logic.model.interfaces.IPFinderAPI;

public class IPFinderAdapter implements CurrentIPFinder {
	
	private IPFinderAPI ipFinder;
	
	public IPFinderAdapter(IPFinderAPI ipfind) {
		this.ipFinder = ipfind;
	}

	@Override
	public String getCurrentIP() {
		return ipFinder.getUserIP();
	}

}
