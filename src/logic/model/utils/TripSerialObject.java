package logic.model.utils;
import java.io.Serializable;
import java.util.List;

import logic.model.Trip;

public class TripSerialObject implements Serializable {

	private static final long serialVersionUID = 7664940401336065330L;
	private List<Trip> list;
	
	public TripSerialObject(List<Trip> list) {
		this.setList(list);
	}

	public List<Trip> getList() {
		return list;
	}

	public void setList(List<Trip> list) {
		this.list = list;
	}
}
