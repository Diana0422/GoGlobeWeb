package logic.model.utils;
import logic.model.exceptions.APIException;
import logic.model.exceptions.IPNotFoundException;
import logic.model.exceptions.LocationNotFoundException;
import logic.model.factories.IPFindGeolocationFactory;
import logic.model.factories.WhoIsGeolocationFactory;

public class GeolocationPicker {

	private static GeolocationPicker instance = null;
	
	private GeolocationPicker() {}
	
	public static GeolocationPicker getInstance() {
		if (instance == null) {
			instance = new GeolocationPicker();
		}
		return instance;
	}
	
	private IPFindGeolocationFactory pickIPFindGeolocationFactory() {
		return IPFindGeolocationFactory.getFactoryInstance();
	}
	
	private WhoIsGeolocationFactory pickWhoIsGeolocationFactory() {
		return WhoIsGeolocationFactory.getFactoryInstance();
	}
	
	public String forwardIPRequestToAPI() throws IPNotFoundException {
		try {
			return pickIPFindGeolocationFactory().getIPFinderAdapter().getCurrentIP();
		} catch (APIException e) {
			try {
				return pickWhoIsGeolocationFactory().getIPFinderAdapter().getCurrentIP();
			} catch (APIException e1) {
				throw new IPNotFoundException(e1.getCause(), "Cannot find user current IP.");
			}
		}
	}
	
	public String forwardLocationRequestToAPI(String ip) throws LocationNotFoundException {
		try {
			return pickIPFindGeolocationFactory().getPositionFinderAdapter().getUserPosition(ip);
		} catch (LocationNotFoundException e) {
			try {
				return pickWhoIsGeolocationFactory().getPositionFinderAdapter().getUserPosition(ip);
			} catch (LocationNotFoundException e1) {
				throw new LocationNotFoundException(e1.getCause(), "Cannot find user current position.");
			}
		}
	}
}
