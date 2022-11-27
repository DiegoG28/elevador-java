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
					while (!this.areThereReq) {
						for (int floor = 0; floor < req.floors.length; floor++) {
							if (req.floors[floor]) {
								this.direction = "up";
								this.areThereReq = true;
							}
						}
					}
				} else if (this.direction == "up") {
					for (int currentFloor = 0; currentFloor < req.floors.length; currentFloor++) {
						sleep(1000);
						System.out.println("Current floor " + currentFloor);
						if (this.areThereReq) {
							for (int currentRequest = 0; currentRequest < req.requests.size(); currentRequest++) {
								Request currentReq = (Request) req.requests.get(currentRequest);
								if (currentFloor == currentReq.finalFloor && currentReq.state == false) {
									this.passengers -= currentReq.people;
									System.out.println(currentReq.people + " passengers left the elevator. Passengers: "
											+ this.passengers + " Direction: " + this.direction);
									currentReq.state = true;
									req.floors[currentFloor] = false;
								}

								if (currentFloor == currentReq.originFloor) {
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

							areThereReq = false;
							for (int i = 0; i < req.requests.size(); i++) {
								Request currentReq = (Request) req.requests.get(i);
								if (currentReq.state == false) {
									areThereReq = true;
								}
							}

							if (!areThereReq) {
								this.direction = "down";
								this.setCurrentFloor(currentFloor);
								break;
							}
						}
					}
				} else if (this.direction == "down") {
					for (int currentFloor = getCurrentFloor(); currentFloor > 0; currentFloor--) {
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

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getCurrentFloor() {
		return this.currentFloor;
	}
}
