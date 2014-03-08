package lab5.simulator;

import carwash.state.CarWashState;
import carwash.view.CarWashView;

public class Simulator {

	EventQueue queue = new EventQueue();
	CarWashView view = new CarWashView();

	// The first event (SimulationStarts) will be created in CarWashState and
	// added to the event queue.
	CarWashState state = new CarWashState(queue, 15);

	// Keep executing events while there are still some events left.
	public void run() {
		do {
			Event event = queue.first();
			queue.removeFirst();
			event.execute();
		} while (queue.size() != 0);
	}

}
