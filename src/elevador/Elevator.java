package elevador;

public class Elevator extends Thread {
	Move move;
	int currentFloor;
	int capacity;
	int passengers;
	int trips;
	boolean direction;
	Request req;

	public Elevator(Request req) {
		this.move = new Move(0, 0);
		this.currentFloor = 0;
		this.capacity = 9;
		this.passengers = 0;
		this.trips = 0;
		this.direction = true;
		this.req = req;
	}

	// si se puede :)
	public void run() {
		try {
			while (true) {
				if (this.currentFloor == 0) {
					for (int i = 0; i < req.floors.length; i++) {
						if (req.floors[i]) {
							move.setInitialFloor(this.currentFloor);
							move.setDestinationFloor(i);
							move.printMove(this.direction);
							req.floors[i] = false;
							this.currentFloor = i;
							Thread.sleep(1000);
							synchronized (req) {
								req.notify();
							}
						}
					}
				} else {
					for (int i = this.currentFloor; i < req.floors.length; i++) {
						if (req.floors[i]) {
							System.out.println("New request");
							req.floors[i] = false;
							this.currentFloor = i;
						}
					}
				}
			}

		} catch (Exception e) {

		}
	}
}
