package logic.model.factories;

import logic.model.adapters.IPLocationAdapter;
import logic.model.apis.IPFindAPI;

public class PositionAdapterFactory {
	
	private static PositionAdapterFactory instance;
	
	private PositionAdapterFactory() {
		// empty constructor
	}
	
	public static PositionAdapterFactory getInstance() {
		if (instance == null) {
			instance = new PositionAdapterFactory();
		}
		
		return instance;
	}
	
	public IPLocationAdapter createIPLocationAdapter() {
		return new IPLocationAdapter(new IPFindAPI());
	}

}
