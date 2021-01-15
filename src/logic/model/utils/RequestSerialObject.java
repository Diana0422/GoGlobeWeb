package logic.model.utils;

import java.io.Serializable;
import java.util.List;

import logic.model.Request;

public class RequestSerialObject implements Serializable {

	private static final long serialVersionUID = -21505970818213310L;
	private List<Request> list;
	
	public RequestSerialObject(List<Request> list) {
		this.list = list;
	}
	
	public List<Request> getList() {
		return list;
	}
	public void setList(List<Request> list) {
		this.list = list;
	}
	

}
