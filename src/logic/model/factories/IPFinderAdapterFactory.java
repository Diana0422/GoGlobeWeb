package logic.model.factories;

import logic.model.adapters.IPFinderAdapter;
import logic.model.apis.WHOISImplementation;

public class IPFinderAdapterFactory {
	
	private static IPFinderAdapterFactory instance = null;
	
	private IPFinderAdapterFactory() {
		// empty constructor
	}
	
	public static IPFinderAdapterFactory getInstance() {
		if (instance == null) {
			instance = new IPFinderAdapterFactory();
		}
		
		return instance;
	}
	
	public IPFinderAdapter createIPFinderAdapter() {
		return new IPFinderAdapter(new WHOISImplementation());
	}
	
}
