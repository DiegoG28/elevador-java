package elevador;

public class Elevator extends Building {
	Move move;
	int capacity;
	int peopleInElevator;
	boolean direction;

	public Elevator() {
		this.move = new Move(0, 0);
		this.capacity = 9;
		this.peopleInElevator = 0;
		this.direction = true;
	}

	public void call(int initialFloor, int destinationFloor, int numberOfPeople) {
		System.out.println("Llamando al ascendor en el piso " + initialFloor + " con destino al piso " + destinationFloor
				+ " con " + numberOfPeople + " personas. " + "Capacidad disponible: " + availableCapacity());
	}

	public void checkFloors(int currentFloor) {
		if (direction) {
			for (int i = currentFloor; i < floors.length; i++) {
				if (floors[i]) {
					this.direction = true;
				} else {
					this.direction = false;
				}
			}
		} else {
			for (int i = currentFloor; i >= 0; i--) {
				if (floors[i]) {
					this.direction = false;
				} else {
					this.direction = true;
				}
			}
		}
	}

	public void fillCapacity(int people) {
		this.peopleInElevator += people;
	}

	public int availableCapacity() {
		return this.capacity - this.peopleInElevator;
	}

	public boolean isFull() {
		return this.peopleInElevator == this.capacity;
	}
}
