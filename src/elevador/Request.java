package elevador;

import java.util.ArrayList;

public class Request {
	ArrayList<Object> requests;

	int originFloor;
	int destinationFloor;
	int people;
	String direction;
	boolean state;

	public Request() {
		this.requests = new ArrayList<Object>();
	}

	public Request(int originFloor, int destinationFloor, int people, String direction) {
		this.originFloor = originFloor;
		this.destinationFloor = destinationFloor;
		this.people = people;
		this.direction = direction;
		this.state = false;
	}

	public void makeRequest(int originFloor, int destinationFloor, int people, String direction) {
		requests.add(new Request(originFloor, destinationFloor, people, direction));
	}

}