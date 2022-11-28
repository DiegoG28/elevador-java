package elevador;

public class Main {

	public static void main(String[] args) {
		Request req = new Request();
		Runnable elevator = new Elevator(req);
		Thread elevatorThread = new Thread(elevator);

		Trip trip1 = new Trip(3, 6, 3, req);
		Trip trip2 = new Trip(7, 10, 3, req);
		Trip trip3 = new Trip(10, 12, 3, req);

		elevatorThread.start();
		trip1.start();
		trip2.start();
		trip3.start();

	}

}
