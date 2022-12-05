package elevador;

import java.util.ArrayList;

public class Request {
	ArrayList<Object> requests;

	int originFloor;
	int destinationFloor;
	int people;
	String direction;
	String state;

	public Request() {
		this.requests = new ArrayList<Object>();
	}

	public Request(int originFloor, int destinationFloor, int people, String direction) {
		this.originFloor = originFloor;
		this.destinationFloor = destinationFloor;
		this.people = people;
		this.direction = direction;
		this.state = "pending";
	}

	public void makeRequest(int originFloor, int destinationFloor, int people, String direction) {
		requests.add(new Request(originFloor, destinationFloor, people, direction));
	}

	public boolean isPending() {
		if (this.state.equals("pending")) {
			return true;
		}
		return false;
	}

	public boolean isInProgress() {
		if (this.state.equals("in progress")) {
			return true;
		}
		return false;
	}

	public boolean isDone() {
		if (this.state.equals("done")) {
			return true;
		}
		return false;
	}

	public boolean isGoingUp() {
		if (this.direction.equals("up")) {
			return true;
		}
		return false;
	}

	public boolean isGoingDown() {
		if (this.direction.equals("down")) {
			return true;
		}
		return false;
	}

	public int getPendingRequests() {
		int pendingRequests = 0;
		for (int currentRequest = 0; currentRequest < requests.size(); currentRequest++) {
			Request currentReq = (Request) requests.get(currentRequest);
			if (currentReq.isPending()) {
				pendingRequests++;
			}
		}
		return pendingRequests;
	}

}