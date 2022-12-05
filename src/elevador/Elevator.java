package elevador;

public class Elevator extends Building implements Runnable {
	int currentFloor;
	int capacity;
	int passengers;
	int trips;
	String direction;
	Request req;

	public Elevator(Request req) {
		this.currentFloor = 0;
		this.capacity = 9;
		this.passengers = 0;
		this.trips = 0;
		this.direction = "stop";
		this.req = req;
	}

	public void run() {
		try {
			while (trips < 10) {
				if (getDirection() == "stop") {
					if (getCurrentFloor() == 0 && !thereArePendingRequests()) {
						System.out.println("There are not pending requests. Waiting...");
						synchronized (req) {
							req.wait();
						}
					}
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						changeDirection();
						if (getDirection() != "stop") {
							break;
						}
						Thread.sleep(250);
						move(currentFloor);
					}
				} else if (getDirection() == "up") {
					for (int currentFloor = getCurrentFloor(); currentFloor < this.floors; currentFloor++) {
						changeDirection();
						if (getDirection() != "up") {
							break;
						}

						Thread.sleep(250);
						move(currentFloor);
						checkForRequest();
					}
				} else if (getDirection() == "down") {
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						changeDirection();
						if (getDirection() != "down") {
							break;
						}

						Thread.sleep(250);
						move(currentFloor);
						checkForRequest();
					}
				}
			}
			System.out.println("The elevator has made " + this.trips + " trips.");
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
		} else if (getDirection() == "stop") {
			if (thereArePendingRequests("up")) {
				setDirection("up");
			} else if (thereArePendingRequests("down")) {
				setDirection("down");
			}
		}
	}

	public void move(int floor) {
		setCurrentFloor(floor);
		System.out.println("Current floor " + getCurrentFloor());
	}

	public void checkForRequest() {
		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);
			// Deja a los pasajeros
			if (isDestinationFloor(currentReq)) {
				setPassengers(-currentReq.people);
				System.out.println(currentReq.people + " passengers left the elevator. Passengers: "
						+ getPassengers() + " Direction: " + getDirection());
				currentReq.state = "done";
				this.trips++;
			}
		}

		for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
			Request currentReq = (Request) req.requests.get(currentRequest);
			// Carga a los pasajeros
			if (isOriginFloor(currentReq)) {

				if (!thereIsSpaceAvailable(currentReq)) {
					System.out.println(currentReq.people + " tried to enter the elevator but there was no space available.");
					break;
				}

				if (thereIsSpaceAvailable(currentReq)
						&& (getDirection() == currentReq.direction || req.getPendingRequests() == 1)) {
					if (req.getPendingRequests() == 1) {
						setDirection(currentReq.direction);
					}
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
				if ((currentReq.originFloor <= getCurrentFloor() && currentReq.isPending())
						|| currentReq.destinationFloor <= getCurrentFloor() && currentReq.isInProgress()) {
					return true;
				}
			}
		}
		return false;
	}

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
		if (getPassengers() + req.people <= this.capacity) {
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
