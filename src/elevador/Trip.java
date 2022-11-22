package elevador;

public class Trip extends Thread {
	Elevator elevator;
	int initialFloor;
	int destinationFloor;
	int numberOfPeople;
	boolean direction;

	public Trip(Elevator elevator, int initialFloor, int destinationFloor, int numberOfPeople) {
		this.elevator = elevator;
		this.initialFloor = initialFloor;
		this.destinationFloor = destinationFloor;
		this.numberOfPeople = numberOfPeople;
		if (initialFloor < destinationFloor) {
			this.direction = true;
		} else {
			this.direction = false;
		}
	}

	public void run() {
		try {
			int randomTime = (int) ((Math.random() * 10 + 1) * 1000);
			elevator.floors[initialFloor] = true;
			sleep(randomTime);
			while ((numberOfPeople > elevator.availableCapacity()) || elevator.direction != this.direction) {
				System.out.println("oal");
				synchronized (elevator) {
					elevator.wait();
				}
			}

			elevator.fillCapacity(numberOfPeople);
			elevator.call(initialFloor, destinationFloor, numberOfPeople);
			sleep(Math.abs(destinationFloor - initialFloor) * 1000);
			elevator.checkFloors(destinationFloor);
			System.out.println("Llegando al piso " + destinationFloor);
			elevator.floors[initialFloor] = false;
			elevator.fillCapacity(-numberOfPeople);
			notifyAll();

		} catch (Exception e) {

		}
	}
}
