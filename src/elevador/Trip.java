package elevador;

public class Trip extends Thread {
	int initialFloor;
	int destinationFloor;
	int people;
	String direction;
	Request req;

	// adios adios:)
	public Trip(int initialFloor, int destinationFloor, int people, Request req) {
		this.initialFloor = initialFloor;
		this.destinationFloor = destinationFloor;
		this.people = people;
		if (this.initialFloor < this.destinationFloor) {
			this.direction = "up";
		} else {
			this.direction = "down";
		}
		this.req = req;
	}

	public void run() {
		try {
			sleep((long) (Math.random() * 2000 + 1000));
			req.floors[this.initialFloor] = true;
			req.makeRequest(initialFloor, destinationFloor, people, direction);
			System.out.println("Travel request from floor " + this.initialFloor + " to " + this.destinationFloor
					+ " Passengers: " + this.people);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
