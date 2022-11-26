package elevador;

public class Move {
	int initialFloor;
	int destinationFloor;

	public Move(int initialFloor, int destinationFloor) {
		this.initialFloor = initialFloor;
		this.destinationFloor = destinationFloor;
	}

	public void printMove(boolean direction) {
		for (int i = this.initialFloor; i < this.destinationFloor; i++) {
			System.out.println("Current floor " + i);
		}
	}

	public void setInitialFloor(int initialFloor) {
		this.initialFloor = initialFloor;
	}

	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}

}
