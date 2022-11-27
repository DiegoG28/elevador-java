package elevador;

public class Main {

	public static void main(String[] args) {
		Request req = new Request();

		Elevator elevator = new Elevator(req);
		Trip trip1 = new Trip(3, 6, 3, req);
		Trip trip2 = new Trip(7, 10, 3, req);
		Trip trip3 = new Trip(10, 12, 3, req);

		elevator.start();
		trip1.start();
		trip2.start();
		trip3.start();

	}

}
