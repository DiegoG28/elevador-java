package elevador;

public class Main {

	public static void main(String[] args) {
		Elevator elevator = new Elevator();

		Trip trip1 = new Trip(elevator, 3, 6, 3);
		Trip trip2 = new Trip(elevator, 7, 10, 3);
		Trip trip3 = new Trip(elevator, 10, 14, 3);

		trip1.start();
		trip2.start();
		trip3.start();

	}

}
