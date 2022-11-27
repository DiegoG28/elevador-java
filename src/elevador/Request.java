package elevador;

import java.util.ArrayList;

public class Request {
	boolean[] floors;
	ArrayList<Object> requests;

	int originFloor;
	int finalFloor;
	int people;
	String direction;
	boolean state;

	public Request() {
		this.floors = new boolean[15];
		this.requests = new ArrayList<Object>();
	}

	public Request(int originFloor, int finalFloor, int people, String direction) {
		this.originFloor = originFloor;
		this.finalFloor = finalFloor;
		this.people = people;
		this.direction = direction;
		this.state = false;
	}

	public void makeRequest(int originFloor, int finalFloor, int people, String direction) {
		requests.add(new Request(originFloor, finalFloor, people, direction));
	}

}