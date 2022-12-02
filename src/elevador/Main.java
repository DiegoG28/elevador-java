package elevador;

public class Main {

	public static void main(String[] args) {
		Request req = new Request();
		Runnable elevator = new Elevator(req);
		Thread elevatorThread = new Thread(elevator);

		Trip trip1 = new Trip(3, 6, 3, req);
		Trip trip2 = new Trip(7, 10, 6, req);
		Trip trip3 = new Trip(10, 12, 6, req);
		Trip trip4 = new Trip(11, 2, 3, req);
		Trip trip5 = new Trip(1, 14, 5, req);
		Trip trip6 = new Trip(12, 0, 4, req);

		elevatorThread.start();
		trip1.start();
		trip2.start();
		trip3.start();
		trip4.start();
		trip5.start();
		trip6.start();

	}

}
