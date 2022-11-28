package elevador;

public class Elevator extends Thread {
	Move move;
	int currentFloor;
	int capacity;
	int passengers;
	int trips;
	String direction;
	Request req;
	Boolean areThereReq;

	public Elevator(Request req) {
		this.move = new Move(0, 0);
		this.currentFloor = 0;
		this.capacity = 9;
		this.passengers = 0;
		this.trips = 0;
		this.direction = "stop";
		this.req = req;
		this.areThereReq = false;
	}

	// si se puede :)
	public void run() {
		try {
			while (trips < 3) {
				if (this.direction == "stop") {
					System.out.println("Elevator is stopped");
					while (true) {
						System.out.print("");
						if (thereArePendingRequests()) {
							setDirection("up");
							break;
						}
					}
				} else if (this.direction == "up") {
					System.out.println("Elevator is going up");
					for (int currentFloor = 0; currentFloor < req.floors.length; currentFloor++) {
						sleep(1000);
						move(currentFloor);

						if (!thereArePendingRequests()) {
							setDirection("down");
							break;
						}
					}
				} else if (this.direction == "down") {
					System.out.println("Elevator is going down");
					for (int currentFloor = getCurrentFloor(); currentFloor >= 0; currentFloor--) {
						sleep(1000);
						System.out.println("Current floor " + currentFloor);
						if (currentFloor == 0) {
							this.direction = "stop";
						}
					}
				}
			}
		} catch (

		Exception e) {

		}
	}

	public void move(int currentFloor) {
		setCurrentFloor(currentFloor);
		System.out.println("Current floor " + getCurrentFloor());
		if (thereArePendingRequests()) {
			for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
				Request currentReq = (Request) req.requests.get(currentRequest);
				if (isDestinationFloor(currentReq)) {
					setPassengers(-currentReq.people);
					System.out.println(currentReq.people + " passengers left the elevator. Passengers: "
							+ getPassengers() + " Direction: " + getDirection());
					currentReq.state = true;
					req.floors[currentFloor] = false;
				}

				if (getCurrentFloor() == currentReq.originFloor) {
					if (req.requests.size() == 1) {
						this.direction = currentReq.direction;
					}

					if (this.passengers + currentReq.people <= this.capacity) {
						this.passengers += currentReq.people;
						System.out.println(currentReq.people + " passengers entered the elevator. Passengers: "
								+ this.passengers + " Direction: " + this.direction);

					}
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

	public boolean isDestinationFloor(Request req) {
		if (getCurrentFloor() == req.destinationFloor && req.state == false) {
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
