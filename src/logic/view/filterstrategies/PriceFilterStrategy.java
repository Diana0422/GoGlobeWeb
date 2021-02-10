package logic.view.filterstrategies;

import java.util.List;

import logic.bean.TripBean;

public class PriceFilterStrategy implements TripFilter {

	@Override
	public List<TripBean> filter(List<TripBean> trips) {
		List<TripBean> filteredTrips = trips;
		if (trips.size() > 1) {
			for (int i = 0;  i < filteredTrips.size()-i; i++) {
				if (compareByPrice(filteredTrips.get(i), filteredTrips.get(i+1))) {
					TripBean temp = filteredTrips.get(i);
					filteredTrips.set(i, filteredTrips.get(i+1));
					filteredTrips.set(i+1, temp);
				}
			}
		}
		return filteredTrips;
	}

	private boolean compareByPrice(TripBean tripBean1, TripBean tripBean2) {
		return tripBean1.getPrice()>=tripBean2.getPrice();
	}

}
