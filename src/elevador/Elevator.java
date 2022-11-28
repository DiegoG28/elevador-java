package elevador;

public class Elevator extends Building implements Runnable {
	Move move;
	int currentFloor;
	int capacity;
	int passengers;
	int trips;
	String direction;
	Request req;

	public Elevator(Request req) {
		this.move = new Move(0, 0);
		this.currentFloor = 0;
		this.capacity = 9;
		this.passengers = 0;
		this.trips = 0;
		this.direction = "stop";
		this.req = req;
	}

	// si se puede :)
	public void run() {
		try {
			while (trips < 3) {
				if (getDirection() == "stop") {
					System.out.println("There are not pending requests. Elevator is going down");
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						if (thereArePendingRequests()) {
							setDirection("up");
							break;
						}

						Thread.sleep(1000);
						move(currentFloor);
					}
				} else if (getDirection() == "up") {
					for (int currentFloor = 0; currentFloor < this.floors; currentFloor++) {
						if (!thereArePendingRequests("up")) {
							setDirection("down");
							break;
						}

						Thread.sleep(1000);
						move(currentFloor);
						checkForRequest();
					}
				} else if (getDirection() == "down") {
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						if (!thereArePendingRequests("down")) {
							setDirection("stop");
							break;
						}

						Thread.sleep(1000);
						move(currentFloor);
						checkForRequest();
					}
				}
			}
		} catch (

		Exception e) {

		}
	}

	public void move(int floor) {
		setCurrentFloor(floor);
		System.out.println("Current floor " + getCurrentFloor());
	}

	public void checkForRequest() {
		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);
			if (isDestinationFloor(currentReq)) {
				setPassengers(-currentReq.people);
				System.out.println(currentReq.people + " passengers left the elevator. Passengers: "
						+ getPassengers() + " Direction: " + getDirection());
				currentReq.state = true;
			}

			if (isOriginFloor(currentReq)) {
				if (shouldChangeDirection()) {
					setDirection(currentReq.direction);
				}

				if (thereIsSpaceAvailable(currentReq)) {
					setPassengers(currentReq.people);
					System.out.println(currentReq.people + " passengers entered the elevator. Passengers: "
							+ this.passengers + " Direction: " + this.direction);

				}
			}
		}
	}

	public boolean thereArePendingRequests() {
		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);
			if (currentReq.state == false) {
				return true;
			}
		}
		return false;
	}

	public boolean thereArePendingRequests(String direction) {
		if (direction.equals("up")) {
			for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
				Request currentReq = (Request) req.requests.get(currentRequest);
				if (currentReq.state == false && currentReq.direction.equals("up")) {
					return true;
				}
			}
		} else if (direction.equals("down")) {
			for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
				Request currentReq = (Request) req.requests.get(currentRequest);
				if (currentReq.state == false && currentReq.direction.equals("down")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isOriginFloor(Request req) {
		if (getCurrentFloor() == req.originFloor) {
			return true;
		}
		return false;
	}

	public boolean isDestinationFloor(Request req) {
		if (getCurrentFloor() == req.destinationFloor && req.state == false) {
			return true;
		}
		return false;
	}

	public boolean thereIsSpaceAvailable(Request req) {
		if (this.passengers + req.people <= this.capacity) {
			return true;
		}
		return false;
	}

	public boolean shouldChangeDirection() {
		if (req.requests.size() == 1) {
			return true;
		}
		return false;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getCurrentFloor() {
		return this.currentFloor;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setPassengers(int passengers) {
		this.passengers += passengers;
	}

	public int getPassengers() {
		return this.passengers;
	}
}
