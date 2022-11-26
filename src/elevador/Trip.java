package elevador;

public class Trip extends Thread {
	int initialFloor;
	int destinationFloor;
	int people;
	boolean direction;
	Request req;

	public Trip(int initialFloor, int destinationFloor, int people, Request req) {
		this.initialFloor = initialFloor;
		this.destinationFloor = destinationFloor;
		this.people = people;
		this.req = req;
	}

	public void run() {
		try {
			int time = (int) (Math.random() * 2) + 1;
			Thread.sleep(time * 1000);
			synchronized (req) {
				System.out.println("Trip has been requested from " + initialFloor + " to " + destinationFloor);
				req.floors[initialFloor] = true;
				req.wait();
				System.out.println("The elevator has arrived at the floor " + initialFloor);
				doTrip();
			}

		} catch (Exception e) {
		}
	}

	public void doTrip() {
		try {
			Thread.sleep(1000);
			System.out.println("Ya llegó al piso " + destinationFloor);
		} catch (Exception e) {

		}
	}
}
