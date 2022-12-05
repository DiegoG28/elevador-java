package elevador;

public class Main {

	public static void main(String[] args) {
		Request req = new Request();
		Runnable elevator = new Elevator(req);
		Thread elevatorThread = new Thread(elevator);

		Trip trip1 = new Trip(req);
		Trip trip2 = new Trip(req);
		Trip trip3 = new Trip(req);
		Trip trip4 = new Trip(req);
		Trip trip5 = new Trip(req);
		Trip trip6 = new Trip(req);
		Trip trip7 = new Trip(req);
		Trip trip8 = new Trip(req);
		Trip trip9 = new Trip(req);
		Trip trip10 = new Trip(req);

		elevatorThread.start();
		trip1.start();
		trip2.start();
		trip3.start();
		trip4.start();
		trip5.start();
		trip6.start();
		trip7.start();
		trip8.start();
		trip9.start();
		trip10.start();

	}

}
