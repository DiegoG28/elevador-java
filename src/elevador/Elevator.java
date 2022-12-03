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

	public void run() {
		try {
			while (trips < 3) {
				if (getDirection() == "stop") {
					Thread.sleep(500);
					System.out.println("There are not pending requests. Elevator is going to floor 0.");
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						changeDirection();
						if (getDirection() != "stop") {
							break;
						}
						Thread.sleep(500);
						move(currentFloor);
					}
				} else if (getDirection() == "up") {
					Thread.sleep(500);
					System.out.println("Elevator is going up.");
					for (int currentFloor = getCurrentFloor(); currentFloor < this.floors; currentFloor++) {
						changeDirection();
						if (getDirection() != "up") {
							break;
						}

						Thread.sleep(500);
						move(currentFloor);
						checkForRequest();
					}
				} else if (getDirection() == "down") {
					Thread.sleep(500);
					System.out.println("Elevator is going down.");
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						changeDirection();
						if (getDirection() != "down") {
							break;
						}

						Thread.sleep(500);
						move(currentFloor);
						checkForRequest();
					}
				}
			}
		} catch (

		Exception e) {

		}
	}

	public void changeDirection() {
		if (getCurrentFloor() == 0 && thereArePendingRequests("up")) {
			setDirection("up");
		} else if (getDirection() == "up" && !thereArePendingRequests("up")) {
			setDirection("down");
		} else if (getDirection() == "down" && !thereArePendingRequests("down")) {
			setDirection("stop");
		}
	}

	public void move(int floor) {
		setCurrentFloor(floor);
		System.out.println("Current floor " + getCurrentFloor());
	}

	public void checkForRequest() {
		// int[] floors = new int[15];
		// for (int currentRequest = 0; currentRequest < req.requests.size();
		// currentRequest++) {
		// Request currentReq = (Request) req.requests.get(currentRequest);
		// if(currentReq)
		// }

		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);
			// System.out.println(getCurrentFloor() + "-" + currentReq.destinationFloor +
			// "-" + currentReq.state);
			// {[1,3],[7,10].[10,12]}
			// piso = 10
			// contador piso1 +1+1
			// contador piso10
			if (isDestinationFloor(currentReq)) {
				setPassengers(-currentReq.people);
				System.out.println(currentReq.people + " passengers left the elevator. Passengers: "
						+ getPassengers() + " Direction: " + getDirection());
				currentReq.state = "done";
			}
		}

		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);

			if (isOriginFloor(currentReq)) {
				// if (shouldChangeDirection()) {
				// setDirection(currentReq.direction);
				// }

				// downnnn
				// 14
				// 13
				// 12
				// 11
				// 10: 10 - 12 direccion up

				// 10 origen <= 14 y está pendiente
				if (thereIsSpaceAvailable(currentReq) && getDirection() == currentReq.direction) {
					setPassengers(currentReq.people);
					System.out.println(currentReq.people + " passengers entered the elevator. Passengers: "
							+ this.passengers + " Direction: " + this.direction);
					currentReq.state = "in progress";
				}
			}

		}

	}

	public boolean thereArePendingRequests() {
		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);
			if (currentReq.isPending() || currentReq.isInProgress()) {
				return true;
			}
		}
		return false;
	}

	public boolean thereArePendingRequests(String direction) {
		if (direction.equals("up")) {

			for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
				Request currentReq = (Request) req.requests.get(currentRequest);

				if ((currentReq.destinationFloor >= getCurrentFloor() && currentReq.isInProgress())
						|| currentReq.originFloor >= getCurrentFloor() && currentReq.isPending()) {
					return true;
				}
			}
		} else if (direction.equals("down")) {
			for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
				Request currentReq = (Request) req.requests.get(currentRequest);
				// System.out.print("abajo?" + currentReq.originFloor + "-" + getCurrentFloor()
				// + " / ");
				if ((currentReq.originFloor <= getCurrentFloor() && currentReq.isPending())
						|| currentReq.destinationFloor <= getCurrentFloor() && currentReq.isInProgress()) {
					return true;
				}
			}
		}
		return false;
	}

	// for (int currentRequest = 0; currentRequest < req.requests.size();
	// currentRequest++) {
	// Request currentReq = (Request) req.requests.get(currentRequest);
	// if ((currentReq.isPending() || currentReq.isInProgress()) &&
	// currentReq.originFloor >= getCurrentFloor()) {
	// return true;
	// }
	// }
	// return false;

	public boolean isOriginFloor(Request req) {
		if (getCurrentFloor() == req.originFloor && req.isPending()) {
			return true;
		}
		return false;
	}

	public boolean isDestinationFloor(Request req) {
		if (getCurrentFloor() == req.destinationFloor && req.isInProgress()) {
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
