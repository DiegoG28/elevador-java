package elevador;

public class Trip extends Thread {
	int initialFloor;
	int destinationFloor;
	int people;
	String direction;
	Request req;

	public Trip(Request req) {
		this.initialFloor = (int) (Math.random() * 15);
		this.destinationFloor = (int) (Math.random() * 15);
		while (this.destinationFloor == this.initialFloor) {
			this.destinationFloor = (int) (Math.random() * 15);
		}
		this.people = (int) (Math.random() * 8) + 1;
		if (this.initialFloor < this.destinationFloor) {
			this.direction = "up";
		} else {
			this.direction = "down";
		}
		this.req = req;
	}

	public void run() {
		try {
			sleep((long) (Math.random() * 30000 + 1000));
			req.makeRequest(initialFloor, destinationFloor, people, direction);
			synchronized (req) {
				req.notify();
			}
			System.out.println("Travel request from floor " + this.initialFloor + " to " + this.destinationFloor
					+ " Passengers: " + this.people);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
